package com.example.exam_quiz_androidapp_firebase.ui.fragment.appfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.example.exam_quiz_androidapp_firebase.R;
import com.example.exam_quiz_androidapp_firebase.data.model.QuizModel;
import com.example.exam_quiz_androidapp_firebase.databinding.FragmentListBinding;
import com.example.exam_quiz_androidapp_firebase.ui.adapter.QuizAdapter;
import com.example.exam_quiz_androidapp_firebase.ui.viewmodel.quiz.QuizViewModel;

import java.util.List;

public class ListFragment extends Fragment implements QuizAdapter.OnItemClicked {
    private FragmentListBinding binding;
    private QuizViewModel viewModel;
    private NavController navController;
    private QuizAdapter mAdapter;
    private Animation fadein;
    private Animation fadeout;


    public ListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        RecyclerView recyclerView = binding.recyclerView;
        ProgressBar progressBar = binding.progressBarList;

        fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

        mAdapter = new QuizAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        viewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<List<QuizModel>>() {
            @SuppressLint("NotifyDataSetChanged") //
            @Override
            public void onChanged(List<QuizModel> quizModelList) {

                // Adapter'i güncelle ve animasyonları ayarla
                mAdapter.setQuizModelData(quizModelList);
                recyclerView.setAnimation(fadein);
                progressBar.setAnimation(fadeout);
            }
        });
    }

    @Override
    public void somethingClicked(int position) {
        ListFragmentDirections.ToDetail action = ListFragmentDirections.toDetail();
        action.setPosition(position);
        navController.navigate(action);

    }
}
