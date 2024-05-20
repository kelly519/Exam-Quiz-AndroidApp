package com.example.exam_quiz_androidapp_firebase.ui.fragment.appfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exam_quiz_androidapp_firebase.R;
import com.example.exam_quiz_androidapp_firebase.databinding.FragmentResultBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultFragment extends Fragment implements View.OnClickListener {
    private FragmentResultBinding binding;
    private NavController navController;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String quizid, currentUserId;

    public ResultFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        quizid = ResultFragmentArgs.fromBundle(getArguments()).getQuizid();
        currentUserId = ResultFragmentArgs.fromBundle(getArguments()).getUserId();

        /*ResultFragmentArgs args = ResultFragmentArgs.fromBundle(getArguments());
        long quizid = args.getQuizid();
        String currentUserId = args.getUserId();

        binding.textViewQuizId.setText(String.valueOf(quizid));
        binding.textViewUserId.setText(currentUserId) */

        binding.buttonGoToHome.setOnClickListener(this);

        firebaseFirestore.collection("QuizList")
                .document(quizid)
                .collection("Results")
                .document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                        if (task.isSuccessful()) {


                            DocumentSnapshot snapshot = task.getResult();

                            Long answerofcorrect = snapshot.getLong("correct");
                            Long incorrectanswers = snapshot.getLong("wrong");
                            Long missedquestions = snapshot.getLong("missed");


                            Long total = answerofcorrect + incorrectanswers + missedquestions;
                            Long percent = (answerofcorrect*100)/total;




                            binding.textViewCorrect.setText(answerofcorrect.toString());
                            binding.textViewWrong.setText(incorrectanswers.toString());
                            binding.textViewMissed.setText(missedquestions.toString());



                            binding.textViewResultsPercent.setText(percent.toString() +"%");
                            binding.progressBarResults.setProgress(percent.intValue());


                        } else {

                            binding.textViewResultsPercent.setText(task.getException().toString());
                        }


                    }
                });
    }

    @Override
    public void onClick(View v) {

    }
}