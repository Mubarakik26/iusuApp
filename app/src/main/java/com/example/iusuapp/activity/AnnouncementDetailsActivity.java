package com.example.iusuapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.iusuapp.R;

public class AnnouncementDetailsActivity extends AppCompatActivity {

    private TextView annTitle,annDescription,annDate,annAuthor;
    private ImageButton annBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);

        annTitle= findViewById(R.id.annDetailsTitle);
        annDescription= findViewById(R.id.annDetailsDescription);
        annDate= findViewById(R.id.annDetailsDate);
        annAuthor= findViewById(R.id.annDetailsAuthor);
        annBackBtn= findViewById(R.id.annDetailsBackBtn);


        annTitle.setText(getIntent().getStringExtra("title"));
        annDescription.setText(getIntent().getStringExtra("description"));
        annDate.setText(getIntent().getStringExtra("date"));
        annAuthor.setText("By: "+getIntent().getStringExtra("author"));

        annBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnouncementDetailsActivity.this.finish();
            }
        });
    }
}