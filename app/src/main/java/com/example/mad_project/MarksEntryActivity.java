package com.example.mad_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mad_project.database.DatabaseHelper;
import com.example.mad_project.models.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarksEntryActivity extends AppCompatActivity {
    private TextView tvSubjectName;
    private LinearLayout llStudents;
    private Button btnSaveMarks;
    private DatabaseHelper dbHelper;
    private int subjectId;
    private List<User> students;
    private Map<Integer, EditText> marksInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_entry);

        tvSubjectName = findViewById(R.id.tvSubjectName);
        llStudents = findViewById(R.id.llStudents);
        btnSaveMarks = findViewById(R.id.btnSaveMarks);
        dbHelper = new DatabaseHelper(this);
        marksInputs = new HashMap<>();

        subjectId = getIntent().getIntExtra("subjectId", -1);
        String subjectName = getIntent().getStringExtra("subjectName");
        
        tvSubjectName.setText("Enter Marks for: " + subjectName);

        loadStudents();

        btnSaveMarks.setOnClickListener(v -> saveMarks());
    }

    private void loadStudents() {
        students = dbHelper.getStudentsBySubject(subjectId);
        
        for (User student : students) {
            View studentView = LayoutInflater.from(this).inflate(R.layout.item_student_marks, llStudents, false);
            
            TextView tvStudentName = studentView.findViewById(R.id.tvStudentName);
            EditText etMarks = studentView.findViewById(R.id.etMarks);
            
            tvStudentName.setText(student.getName() + " (" + student.getUsername() + ")");
            
            // Load existing marks if available
            if (dbHelper.hasResult(student.getId(), subjectId)) {
                float existingMarks = dbHelper.getStudentMarks(student.getId(), subjectId);
                etMarks.setText(String.valueOf(existingMarks));
            }
            
            marksInputs.put(student.getId(), etMarks);
            llStudents.addView(studentView);
        }
    }

    private void saveMarks() {
        boolean allValid = true;
        
        for (User student : students) {
            EditText etMarks = marksInputs.get(student.getId());
            String marksText = etMarks.getText().toString().trim();
            
            if (marksText.isEmpty()) {
                etMarks.setError("Required");
                allValid = false;
                continue;
            }
            
            try {
                float marks = Float.parseFloat(marksText);
                if (marks < 0 || marks > 100) {
                    etMarks.setError("Marks must be between 0-100");
                    allValid = false;
                } else if (marksText.contains(".") && marksText.substring(marksText.indexOf(".") + 1).length() > 1) {
                    etMarks.setError("Maximum 1 decimal place");
                    allValid = false;
                }
            } catch (NumberFormatException e) {
                etMarks.setError("Enter valid marks");
                allValid = false;
            }
        }
        
        if (allValid) {
            for (User student : students) {
                EditText etMarks = marksInputs.get(student.getId());
                float marks = Float.parseFloat(etMarks.getText().toString().trim());
                dbHelper.saveResult(student.getId(), subjectId, marks);
            }
            Toast.makeText(this, "Marks saved successfully", Toast.LENGTH_SHORT).show();
            
            // Navigate back to professor dashboard with user data
            int professorId = getIntent().getIntExtra("professorId", -1);
            String professorName = getIntent().getStringExtra("professorName");
            
            android.util.Log.d("MARKS_ENTRY", "Returning to dashboard with Professor ID: " + professorId + ", Name: " + professorName);
            
            Intent intent = new Intent(this, ProfessorDashboardActivity.class);
            intent.putExtra("userId", professorId);
            intent.putExtra("userName", professorName);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }
}