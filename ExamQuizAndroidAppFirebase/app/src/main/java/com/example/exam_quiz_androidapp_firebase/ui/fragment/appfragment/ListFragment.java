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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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

        fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setHasFixedSize(true);

        viewModel.getLiveDatafromFireStore().observe(getViewLifecycleOwner(), new Observer<List<QuizModel>>() {
            @SuppressLint("NotifyDataSetChanged") //
            @Override
            public void onChanged(List<QuizModel> quizModelList) {

                QuizAdapter mAdapter = new QuizAdapter(requireContext(), quizModelList, (QuizAdapter.OnItemClicked) viewModel);  //  ListFragment.this
                binding.recyclerView.setAdapter(mAdapter);

                // Adapter'i güncelle ve animasyonları ayarla
                mAdapter.setQuizModelData(quizModelList);
                mAdapter.notifyDataSetChanged(); //
                binding.recyclerView.setAnimation(fadein);
                binding.progressBarList.setAnimation(fadeout);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
    }

    @Override
    public void somethingClicked(int position) {
        //////////////////// bu kismi yapamadim atladim bakman lazim 5. video sonu /// tamamdir sonunda yaptim ama sen yinede tam mantigi anla unutma tekrar bak impleme silme olayina bi bak silip calisiyor mu vs diye
        ListFragmentDirections.ToDetail action = ListFragmentDirections.toDetail();
        action.setPosition(position);
        navController.navigate(action);

    }
}
