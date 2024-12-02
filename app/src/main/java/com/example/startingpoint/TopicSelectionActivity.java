package com.example.startingpoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopicSelectionActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "UserPreferences";
    private static final String KEY_TOPICS = "SelectedTopics";

    private ChipGroup chipGroup;
    private Button saveButton;
    private Button addCustomTopicButton;
    private EditText customTopicInput;
    private List<String> predefinedTopics = Arrays.asList(
            "Natural Science", "Music", "Movies", "Technology", "History",
            "Literature", "Art", "Geography", "Sports", "Computer Science"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);

        chipGroup = findViewById(R.id.chipGroup);
        saveButton = findViewById(R.id.saveButton);
        addCustomTopicButton = findViewById(R.id.addCustomTopicButton);
        customTopicInput = findViewById(R.id.customTopicInput);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        for (String topic : predefinedTopics) {
            Chip chip = new Chip(this);
            chip.setText(topic);
            chip.setCheckable(true);

            chip.setChipBackgroundColorResource(R.color.chipBackground);
            chip.setTextColor(getResources().getColor(R.color.chipText));
            chip.setChipStrokeWidth(2f);
            chip.setChipStrokeColorResource(android.R.color.transparent);

            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    chip.setChipStrokeColorResource(R.color.chipStroke);
                    chip.setChipBackgroundColorResource(R.color.chipSelectedBackground);
                    chip.setTextColor(getResources().getColor(R.color.chipSelectedText));
                } else {
                    chip.setChipStrokeColorResource(android.R.color.transparent);
                    chip.setChipBackgroundColorResource(R.color.chipBackground);
                    chip.setTextColor(getResources().getColor(R.color.chipText));
                }
            });

            chipGroup.addView(chip);
        }

        addCustomTopicButton.setOnClickListener(v -> {
            String customTopic = customTopicInput.getText().toString().trim();
            if (!customTopic.isEmpty()) {
                Chip chip = new Chip(this);
                chip.setText(customTopic);
                chip.setCheckable(true);
                chipGroup.addView(chip);
                customTopicInput.setText("");
            } else {
                Toast.makeText(this, "Please enter a topic to add", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(v -> {
            List<String> selectedTopics = new ArrayList<>();
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    selectedTopics.add(chip.getText().toString());
                }
            }

            if (selectedTopics.size() < 3) {
                Toast.makeText(this, "Please select at least 3 topics", Toast.LENGTH_SHORT).show();
                return;
            }

            saveTopics(selectedTopics);

            Intent intent = new Intent(TopicSelectionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void saveTopics(List<String> topics) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> topicSet = new HashSet<>(topics);
        editor.putStringSet(KEY_TOPICS, topicSet);
        editor.putBoolean("FirstLaunch", false);
        editor.apply();
    }
}
