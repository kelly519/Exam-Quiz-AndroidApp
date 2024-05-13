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
import com.example.exam_quiz_androidapp_firebase.databinding.FragmentSignUpBinding;
import com.example.exam_quiz_androidapp_firebase.ui.viewmodel.MainPageViewModel;
import com.example.exam_quiz_androidapp_firebase.ui.viewmodel.SignUpViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private SignUpViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSignUp.setOnClickListener(view1 ->{

            String firstName = binding.editTextFirstName.getText().toString();
            String lastName = binding.editTextLastName.getText().toString();
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            String confirmPassword = binding.editTextConfirmPassword.getText().toString();

            if (!password.equals(confirmPassword)) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return; // Stop the process if passwords don't match
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(requireContext(), "Registration successful. Please log in.", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).popBackStack();

                } else {
                    Toast.makeText(requireContext(), "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });
        binding.textViewlogin.setOnClickListener(view1 -> {
            Navigation.findNavController(requireView()).popBackStack();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

    }
}
