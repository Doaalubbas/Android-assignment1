package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment1Binding;

public class Fragment1 extends Fragment {

    public interface OnGetStartedListener {
        void onGetStarted();
    }

    private Fragment1Binding binding;
    private OnGetStartedListener listener;

    public Fragment1() {
    }

    public static Fragment1 newInstance() {
        return new Fragment1();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnGetStartedListener) {
            listener = (OnGetStartedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnGetStartedListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment1Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonGetStarted.setOnClickListener(v -> listener.onGetStarted());
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
