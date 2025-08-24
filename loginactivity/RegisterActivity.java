package com.example.loginactivity;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUser, etEmail, etPass;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUser = findViewById(R.id.etRegUsername);
        etEmail = findViewById(R.id.etRegEmail);
        etPass  = findViewById(R.id.etRegPassword);
        spinner = findViewById(R.id.spinnerRole);
        Button btn = findViewById(R.id.btnDoRegister);

        String[] roles = {"Student", "Teacher", "Admin"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, roles);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        btn.setOnClickListener(v -> doRegister());
    }

    private void doRegister() {
        String u = etUser.getText().toString().trim();
        String e = etEmail.getText().toString().trim();
        String p = etPass.getText().toString();
        String r = spinner.getSelectedItem().toString();

        boolean ok = true;
        if (u.isEmpty()) { etUser.setError("Required"); ok = false; }
        if (e.isEmpty() || !e.contains("@") || !e.contains(".")) { etEmail.setError("Invalid email"); ok = false; }
        if (p.length() < 6) { etPass.setError("Min 6 chars"); ok = false; }
        if (!ok) return;

        getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .edit()
                .putString("username", u)
                .putString("email", e)
                .putString("password", p)
                .putString("role", r)
                .apply();

        Toast.makeText(this, "Registered. Please login.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
