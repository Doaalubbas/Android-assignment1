package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

public class Fragment2 extends Fragment {

    public interface OnUserInfoSubmittedListener {
        void onUserInfoSubmitted(String name, String email, String gender,
                                 String age, String university);
    }

    private Fragment2Binding binding;
    private OnUserInfoSubmittedListener listener;

    public Fragment2() {
    }

    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnUserInfoSubmittedListener) {
            listener = (OnUserInfoSubmittedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnUserInfoSubmittedListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonContinue.setOnClickListener(v -> submit());
    }

    private void submit() {
        String name = binding.editTextName.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        String age = binding.editTextAge.getText().toString().trim();
        String university = binding.editTextUniversity.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            binding.editTextName.setError(getString(R.string.error_name));
            binding.editTextName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError(getString(R.string.error_email));
            binding.editTextEmail.requestFocus();
            return;
        }
        int checkedId = binding.radioGroupGender.getCheckedRadioButtonId();
        if (checkedId == -1) {
            Toast.makeText(requireContext(), R.string.error_gender, Toast.LENGTH_SHORT).show();
            return;
        }
        String gender = ((RadioButton) binding.getRoot().findViewById(checkedId))
                .getText().toString();

        listener.onUserInfoSubmitted(name, email, gender, age, university);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
