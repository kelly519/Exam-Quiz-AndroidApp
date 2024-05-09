package com.example.exam_quiz_androidapp_firebase.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.exam_quiz_androidapp_firebase.R;
import com.example.exam_quiz_androidapp_firebase.databinding.FragmentMainPageBinding;
import com.example.exam_quiz_androidapp_firebase.ui.viewmodel.MainPageViewModel;
import com.example.exam_quiz_androidapp_firebase.ui.viewmodel.SignUpViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainPageFragment extends Fragment {
    private FragmentMainPageBinding binding;
    private MainPageViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainPageViewModel.class);


        binding.textViewSignUp.setOnClickListener(view2 -> {
            Navigation.findNavController(view2).navigate(R.id.toSignUp);
        });

        binding.buttonLogin.setOnClickListener(view3 -> {

            String user_email = binding.editTextEmail.getText().toString();
            String user_password = binding.editTextPassword.getText().toString();

            viewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean isLoggedIn) {
                    if (isLoggedIn) {
                        Navigation.findNavController(requireView()).navigate(R.id.toHomePage);
                    } else {

                        Toast.makeText(requireContext(), "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            viewModel.login(user_email,user_password);
        });

    }
}






