package com.example.exam_quiz_androidapp_firebase.ui.fragment.appfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.exam_quiz_androidapp_firebase.R;
import com.example.exam_quiz_androidapp_firebase.data.model.QuizModel;
import com.example.exam_quiz_androidapp_firebase.databinding.FragmentDetailBinding;
import com.example.exam_quiz_androidapp_firebase.ui.viewmodel.quiz.QuizViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DetailFragment extends Fragment implements View.OnClickListener {
    private FragmentDetailBinding binding;
    private QuizViewModel viewModel;
    private Context mContext;
    private NavController navController;
    private int position = 0;
    private String currentUserId, quizID, quizName ;
    private long totalQuestions = 0L;

    public DetailFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        position = DetailFragmentArgs.fromBundle(getArguments()).getPosition();

        binding.buttonDetailTakeQuiz.setOnClickListener(this);

        viewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<List<QuizModel>>() {
            @Override
            public void onChanged(List<QuizModel> quizModelList) {

                binding.textViewDetailQuizTitle.setText(quizModelList.get(position).getQuizname());
                binding.textViewDetailQuizDescription.setText(quizModelList.get(position).getDescription());
                binding.textViewDetailQuestions.setText(quizModelList.get(position).getQuestions());
                binding.textViewDetailLevel.setText(quizModelList.get(position).getLevel());

                Glide.with(mContext)
                        .load(quizModelList.get(position).getImage())
                        .placeholder(R.drawable.generalknowladge)
                        .centerCrop().into(binding.imageViewDetailImage);   //iamge view de bir problem var

                quizID = quizModelList.get(position).getQuizid();
                quizName = quizModelList.get(position).getQuizname();
                totalQuestions = quizModelList.get(position).getQuestions();

                LoadRecentResult();

            }
        });

    }

    private void LoadRecentResult(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user  = firebaseAuth.getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        if (user!= null) {


            currentUserId = user.getUid();


            firebaseFirestore.collection("QuizList")
                    .document(quizID)
                    .collection("Results")
                    .document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {


                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                DocumentSnapshot snapshot = task.getResult();

                                // if there are no results in firestore to show you
                                if (snapshot.exists() && snapshot != null) {


                                    Long correctAnswers = snapshot.getLong("correct");
                                    Long incorrectAnswers = snapshot.getLong("wrong");
                                    Long missedQuestions = snapshot.getLong("missed");


                                    long total = correctAnswers + incorrectAnswers + missedQuestions;


                                    long percent = (correctAnswers * 100) / total;

                                    binding.textViewDetailLastScoreText.setText(percent + "%");

                                } else {
                                    binding.textViewDetailLastScoreText.setText(task.getException().toString());
                                }
                            }
                        }

                    });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
    }


    @Override
    public void onClick(View v) {
        DetailFragmentDirections.ToQuiz action = DetailFragmentDirections.toQuiz();

        action.setQuizid(quizID);
        action.setQuizName(quizName);
        action.setTotalNumberOfQuestion(totalQuestions);
        navController.navigate(action);

    }
}