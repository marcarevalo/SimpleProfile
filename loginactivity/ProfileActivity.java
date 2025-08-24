package com.example.loginactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvWelcome, tvDetails, tvRatingValue;
    private RatingBar ratingBar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvDetails = findViewById(R.id.tvDetails);
        tvRatingValue = findViewById(R.id.tvRatingValue);
        ratingBar = findViewById(R.id.ratingBar);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnSettings = findViewById(R.id.btnSettings);

        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String user = prefs.getString("username", "User");
        String role = prefs.getString("role", "Student");

        tvWelcome.setText("Welcome, " + user);
        tvDetails.setText(user + " · " + role);

        float savedRating = prefs.getFloat("rating", 0f);
        ratingBar.setRating(savedRating);
        tvRatingValue.setText("Your rating: " + (int) savedRating);

        ratingBar.setOnRatingBarChangeListener((rb, rating, fromUser) -> {
            tvRatingValue.setText("Your rating: " + (int) rating);
            prefs.edit().putFloat("rating", rating).apply();
        });

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));

        btnDelete.setOnClickListener(v -> confirmDelete());
    }

    @Override
    protected void onResume() {
        super.onResume();
        float size = prefs.getFloat("textSizeSp", 16f);
        tvWelcome.setTextSize(size);
        tvDetails.setTextSize(size);
        tvRatingValue.setTextSize(size);
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete this account?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        prefs.edit().clear().apply();
                        Toast.makeText(ProfileActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .show();
    }
}
