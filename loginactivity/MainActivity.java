package com.example.loginactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private ProgressBar progress;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // uses activity_main.xml

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvGoRegister = findViewById(R.id.tvGoRegister);
        progress = findViewById(R.id.progressLogin);
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        tvGoRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        btnLogin.setOnClickListener(v -> tryLogin());
    }

    private void tryLogin() {
        String u = etUsername.getText().toString().trim();
        String p = etPassword.getText().toString();

        boolean ok = true;
        if (u.isEmpty()) { etUsername.setError("Required"); ok = false; }
        if (p.isEmpty()) { etPassword.setError("Required"); ok = false; }
        if (!ok) return;

        String savedUser = prefs.getString("username", "");
        String savedPass = prefs.getString("password", "");
        String savedRole = prefs.getString("role", "Student");

        if (!u.equals(savedUser) || !p.equals(savedPass)) {
            Toast.makeText(this, "Invalid credentials or not registered", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            progress.setVisibility(View.GONE);
            prefs.edit().putBoolean("isLoggedIn", true).apply();
            Intent i = new Intent(this, ProfileActivity.class);
            i.putExtra("role", savedRole);
            startActivity(i);
            finish();
        }, 2000);
    }
}
