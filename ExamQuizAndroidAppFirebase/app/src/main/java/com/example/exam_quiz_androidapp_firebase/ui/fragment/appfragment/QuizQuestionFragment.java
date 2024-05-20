package com.example.exam_quiz_androidapp_firebase.ui.fragment.appfragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.exam_quiz_androidapp_firebase.R;
import com.example.exam_quiz_androidapp_firebase.data.model.QuestionModel;
import com.example.exam_quiz_androidapp_firebase.databinding.FragmentQuizQuestionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizQuestionFragment extends Fragment implements View.OnClickListener {

    private FragmentQuizQuestionBinding binding;
    private NavController navController;
    private String quizid, currentUserId, quizName;

    private Long totalNumberOfQuestions = 0L;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private int correctAnswer = 0;
    private int wrongAnswer = 0;
    private int missedAnswer = 0;

    private int currentQuestion = 0;
    private boolean canAnswer = false;
    private CountDownTimer countDownTimer;


    List<QuestionModel> allQuestionList = new ArrayList<>();
    List<QuestionModel> questionToAnswer = new ArrayList<>();

    public QuizQuestionFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuizQuestionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        quizid = QuizQuestionFragmentArgs.fromBundle(getArguments()).getQuizid();
        totalNumberOfQuestions = QuizQuestionFragmentArgs.fromBundle(getArguments()).getTotalNumberOfQuestion();
        quizName = QuizQuestionFragmentArgs.fromBundle(getArguments()).getQuizName();


        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            currentUserId = user.getUid();

        } else {
            // go back to homefragment
        }

        firebaseFirestore.collection("QuizList")
                .document(quizid).collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            allQuestionList = task.getResult().toObjects(QuestionModel.class);
                            pickedQuestion();
                            LoadUi();

                        } else {
                            binding.textViewQuizTitleQuiz.setText(task.getException().toString());
                        }

                    }
                });
        binding.buttonOptionA.setOnClickListener(this);
        binding.buttonOptionB.setOnClickListener(this);
        binding.buttonOptionC.setOnClickListener(this);
        binding.buttonQuizNext.setOnClickListener(this);

    }


    private void pickedQuestion() {

        for (int i = 0; i < totalNumberOfQuestions; i++) {

            int randomNumber = getRandomInt(0, allQuestionList.size());
            questionToAnswer.add(allQuestionList.get(randomNumber));
            allQuestionList.remove(randomNumber);

        }
    }

    private int getRandomInt(int min, int max) {
        return ((int) (Math.random() * (max - min))) + min;

    }

    private void LoadUi() {

        binding.textViewQuizTitleQuiz.setText(quizName);
        binding.textViewQuizQuestion.setText("Loading Question");

        EnableOptions();
        LoadQuestion(1);


    }

    private void LoadQuestion(int questionNumber) {

        binding.textViewQuizQuestion.setText(questionNumber + ". " + questionToAnswer.get(questionNumber - 1).getQuestion());
        binding.buttonOptionA.setText(questionToAnswer.get(questionNumber - 1).getOptiona());
        binding.buttonOptionB.setText(questionToAnswer.get(questionNumber - 1).getOptionb());
        binding.buttonOptionC.setText(questionToAnswer.get(questionNumber - 1).getOptionc());

        canAnswer = true;
        currentQuestion = questionNumber;
        startTimer(questionNumber);

    }

    private void startTimer(int questionNumber) {

        Long timeToAnswer = questionToAnswer.get(questionNumber - 1).getTimer();
        binding.textViewTimerText.setText(timeToAnswer.toString());
        binding.progressBarQuiz.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timeToAnswer * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {

                binding.textViewTimerText.setText(millisUntilFinished / 1000 + "");
                Long percent = millisUntilFinished / (timeToAnswer * 10);
                binding.progressBarQuiz.setProgress(percent.intValue());
            }

            @Override
            public void onFinish() {   // mvvm e uygun degil uygun hale getir

                canAnswer = false;
                binding.textViewQuizAnswerFeedback.setText("Time Up Loser");
                binding.textViewQuizAnswerFeedback.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_red));
                missedAnswer++;
                showNextButton();

            }
        };
        countDownTimer.start();
    }

    private void EnableOptions() {

        binding.buttonOptionA.setVisibility(View.VISIBLE);
        binding.buttonOptionB.setVisibility(View.VISIBLE);
        binding.buttonOptionC.setVisibility(View.VISIBLE);
        binding.buttonQuizNext.setVisibility(View.INVISIBLE);
        binding.buttonQuizNext.setEnabled(false);

        binding.buttonOptionA.setEnabled(true);
        binding.buttonOptionB.setEnabled(true);
        binding.buttonOptionC.setEnabled(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.buttonOptionA) {
            verifyAnswer(binding.buttonOptionA);
        } else if (id == R.id.buttonOptionB) {
            verifyAnswer(binding.buttonOptionB);
        } else if (id == R.id.buttonOptionC) {
            verifyAnswer(binding.buttonOptionC);
        } else if (id == R.id.buttonQuizNext) {
            // if you have completed the quiz
            if (currentQuestion == totalNumberOfQuestions) {
                submitResult();
            } else {
                currentQuestion++;
                LoadQuestion(currentQuestion);
                reseOptions();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void reseOptions() {


        binding.buttonOptionA.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));
        binding.buttonOptionB.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));
        binding.buttonOptionC.setBackground(getResources().getDrawable(R.drawable.outline_light_btn_bg, null));

        binding.buttonOptionA.setTextColor(getResources().getColor(R.color.white, null));
        binding.buttonOptionB.setTextColor(getResources().getColor(R.color.white, null));
        binding.buttonOptionC.setTextColor(getResources().getColor(R.color.white, null));


        binding.textViewQuizAnswerFeedback.setVisibility(View.INVISIBLE);
        binding.buttonQuizNext.setVisibility(View.INVISIBLE);
        binding.buttonQuizNext.setEnabled(false);
    }

    private void submitResult() {

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("correct", correctAnswer);
        hashMap.put("wrong", wrongAnswer);
        hashMap.put("missed", missedAnswer);

        firebaseFirestore.collection("QuizList")
                .document(quizid)
                .collection("Results")
                .document(currentUserId)
                .set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {


                            QuizQuestionFragmentDirections.ToResult actions = QuizQuestionFragmentDirections.toResult();

                            actions.setQuizid(quizid);
                            actions.setUserId(currentUserId);
                            navController.navigate(actions);


                        } else {

                            binding.textViewQuizAnswerFeedback.setText(task.getException().toString());
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyAnswer(Button selectedButton) {

        if (canAnswer) {

            selectedButton.setTextColor(getResources().getColor(R.color.white, null));


            if (questionToAnswer.get(currentQuestion - 1).getAnswer().equals(selectedButton.getText())) {

                correctAnswer++;

                selectedButton.setBackgroundColor(getResources().getColor(R.color.dark_green, null));
                binding.textViewQuizAnswerFeedback.setText("Answer is Correct");
                binding.textViewQuizAnswerFeedback.setTextColor(getResources().getColor(R.color.white, null));

            } else {

                selectedButton.setBackgroundColor(getResources().getColor(R.color.dark_red, null));

                wrongAnswer++;
                binding.textViewQuizAnswerFeedback.setText("incorrect");
                binding.textViewQuizAnswerFeedback.setTextColor(getResources().getColor(R.color.dark_red, null));

            }
            canAnswer = false;

            countDownTimer.cancel();
            showNextButton();

        }
    }

    private void showNextButton() {

        // if you have completed the quiz
        if (currentQuestion == totalNumberOfQuestions) {
            binding.buttonQuizNext.setText("Go to Results");


        }

        binding.textViewQuizAnswerFeedback.setVisibility(View.VISIBLE);
        binding.buttonQuizNext.setVisibility(View.VISIBLE);
        binding.buttonQuizNext.setEnabled(true);

    }
}