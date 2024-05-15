package com.example.exam_quiz_androidapp_firebase.ui.fragment.appfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.exam_quiz_androidapp_firebase.R;
import com.example.exam_quiz_androidapp_firebase.databinding.FragmentListBinding;

public class ListFragment extends Fragment {
    private FragmentListBinding binding;

    private Animation fadein;
    private Animation fadeout;

    public ListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //fadein= AnimationUtils.loadAnimation(getContext(),R.anim.)

    }
}