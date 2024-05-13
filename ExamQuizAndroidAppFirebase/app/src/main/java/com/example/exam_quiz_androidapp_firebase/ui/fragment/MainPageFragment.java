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

        binding.textViewSignUp.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.toSignUp);
        });

        binding.buttonLogin.setOnClickListener(view2 -> {
            String user_email = binding.editTextEmail.getText().toString();
            String user_password = binding.editTextPassword.getText().toString();

            viewModel.login(user_email, user_password);

            viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String errorMessage) {
                    if (errorMessage != null) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
                if (firebaseUser != null) {
                    Navigation.findNavController(requireView()).navigate(R.id.toEntrance);
                }
            });
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainPageViewModel.class);
    }
}






