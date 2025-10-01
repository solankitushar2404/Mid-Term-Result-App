package com.example.mad_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mad_project.database.DatabaseHelper;
import com.example.mad_project.models.Result;
import com.example.mad_project.models.Subject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDashboardActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnCheckResults;
    private LinearLayout llResults;
    private DatabaseHelper dbHelper;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnCheckResults = findViewById(R.id.btnCheckResults);
        llResults = findViewById(R.id.llResults);
        dbHelper = new DatabaseHelper(this);

        studentId = getIntent().getIntExtra("userId", -1);
        String studentName = getIntent().getStringExtra("userName");
        String enrollmentNo = getIntent().getStringExtra("enrollmentNo");
        
        tvWelcome.setText(studentName + " (" + enrollmentNo + ")");

        btnCheckResults.setOnClickListener(v -> loadResults());

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadResults() {
        llResults.removeAllViews();
        
        List<Subject> subjects = dbHelper.getSubjectsByStudent(studentId);
        List<Result> results = dbHelper.getResultsByStudent(studentId);
        
        Map<Integer, Float> resultMap = new HashMap<>();
        for (Result result : results) {
            resultMap.put(result.getSubjectId(), result.getMarks());
        }
        
        for (Subject subject : subjects) {
            View resultView = LayoutInflater.from(this).inflate(R.layout.item_student_result, llResults, false);
            
            TextView tvSubjectName = resultView.findViewById(R.id.tvSubjectName);
            TextView tvMarks = resultView.findViewById(R.id.tvMarks);
            
            tvSubjectName.setText(subject.getName() + " (" + subject.getCode() + ")");
            
            if (dbHelper.hasResult(studentId, subject.getId())) {
                float marks = resultMap.get(subject.getId());
                tvMarks.setText("Marks: " + String.format("%.1f", marks) + "/100");
                tvMarks.setTextColor(getResources().getColor(android.R.color.black));
            } else {
                tvMarks.setText("Result is to be declared");
                tvMarks.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            }
            
            llResults.addView(resultView);
        }
    }
}