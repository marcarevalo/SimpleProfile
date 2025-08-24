package com.example.loginactivity;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar seek;
    private TextView tvPreview, tvSizeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seek = findViewById(R.id.seekTextSize);
        tvPreview = findViewById(R.id.tvPreview);
        tvSizeLabel = findViewById(R.id.tvSizeLabel);

        float saved = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .getFloat("textSizeSp", 16f);
        int progress = Math.max(10, Math.min(30, (int) saved));
        seek.setProgress(progress);
        applySize(progress);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                int clamped = Math.max(10, Math.min(30, value));
                applySize(clamped);
                getSharedPreferences("MyPrefs", MODE_PRIVATE)
                        .edit().putFloat("textSizeSp", clamped).apply();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void applySize(int sp) {
        tvPreview.setTextSize(sp);
        tvSizeLabel.setText("Size: " + sp + "sp");
    }
}
