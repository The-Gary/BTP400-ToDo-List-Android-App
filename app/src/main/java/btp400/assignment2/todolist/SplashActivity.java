package btp400.assignment2.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
/**
 *this method handles the creation of the splash activity
 * @param savedInstanceState*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        final Intent intent = new Intent(this, MainActivity.class);
        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 2000);
    }
}