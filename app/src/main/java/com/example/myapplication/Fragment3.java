package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

public class Fragment3 extends Fragment {

    public interface OnConfirmationChangedListener {
        void onConfirmationChanged(boolean isChecked);

        void onFinishClicked(String name);
    }

    private static final String ARG_NAME = "arg_name";
    private static final String ARG_EMAIL = "arg_email";
    private static final String ARG_GENDER = "arg_gender";
    private static final String ARG_AGE = "arg_age";
    private static final String ARG_UNIVERSITY = "arg_university";

    private Fragment3Binding binding;
    private OnConfirmationChangedListener listener;

    private String name;
    private String email;
    private String gender;
    private String age;
    private String university;

    public Fragment3() {
    }

        public static Fragment3 newInstance(String name, String email, String gender,
                                        String age, String university) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_GENDER, gender);
        args.putString(ARG_AGE, age);
        args.putString(ARG_UNIVERSITY, university);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnConfirmationChangedListener) {
            listener = (OnConfirmationChangedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnConfirmationChangedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString(ARG_NAME);
            email = args.getString(ARG_EMAIL);
            gender = args.getString(ARG_GENDER);
            age = args.getString(ARG_AGE);
            university = args.getString(ARG_UNIVERSITY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment3Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewWelcome.setText(getString(R.string.welcome_name, name));
        binding.textViewSummary.setText(buildSummary());

        updateContinueButton(binding.checkBoxConfirm.isChecked());

        binding.checkBoxConfirm.setOnCheckedChangeListener((buttonView, isChecked) ->
                listener.onConfirmationChanged(isChecked));

        binding.buttonContinue.setOnClickListener(v -> listener.onFinishClicked(name));
    }

    public void updateContinueButton(boolean confirmed) {
        if (binding == null) return;
        binding.buttonContinue.setEnabled(confirmed);
        binding.buttonContinue.setText(confirmed ? R.string.finish : R.string.continue_text);
    }

    private String buildSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.label_email)).append(": ").append(email);
        sb.append("\n").append(getString(R.string.label_gender)).append(": ").append(gender);
        if (!TextUtils.isEmpty(age)) {
            sb.append("\n").append(getString(R.string.label_age)).append(": ").append(age);
        }
        if (!TextUtils.isEmpty(university)) {
            sb.append("\n").append(getString(R.string.label_university)).append(": ").append(university);
        }
        return sb.toString();
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
