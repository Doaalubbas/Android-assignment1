package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        Fragment1.OnGetStartedListener,
        Fragment2.OnUserInfoSubmittedListener,
        Fragment3.OnConfirmationChangedListener {

    private static final String TAG_FRAGMENT_3 = "fragment3";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, Fragment1.newInstance())
                    .commit();
        }
    }

    @Override
    public void onGetStarted() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment2.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onUserInfoSubmitted(String name, String email, String gender,
                                    String age, String university) {
        Fragment3 fragment3 = Fragment3.newInstance(name, email, gender, age, university);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment3, TAG_FRAGMENT_3)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onConfirmationChanged(boolean isChecked) {
        Fragment3 fragment3 = (Fragment3) getSupportFragmentManager()
                .findFragmentByTag(TAG_FRAGMENT_3);
        if (fragment3 != null) {
            fragment3.updateContinueButton(isChecked);
        }
    }

    @Override
    public void onFinishClicked(String name) {
        Toast.makeText(this,
                getString(R.string.toast_finished, name),
                Toast.LENGTH_SHORT).show();
    }
}
