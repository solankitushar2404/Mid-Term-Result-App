package com.example.mad_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mad_project.database.DatabaseHelper;
import com.example.mad_project.models.Subject;
import java.util.List;

public class ProfessorDashboardActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private ListView lvSubjects;
    private DatabaseHelper dbHelper;
    private int professorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        lvSubjects = findViewById(R.id.lvSubjects);
        dbHelper = new DatabaseHelper(this);

        professorId = getIntent().getIntExtra("userId", -1);
        String professorName = getIntent().getStringExtra("userName");
        
        if (professorName != null) {
            tvWelcome.setText(professorName);
        }

        if (professorId != -1) {
            loadSubjects();
        }

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadSubjects() {
        if (professorId == -1) {
            android.util.Log.e("PROF_DASH", "Invalid professor ID");
            return;
        }
        
        List<Subject> subjects = dbHelper.getSubjectsByProfessor(professorId);
        android.util.Log.d("PROF_DASH", "Loading subjects for professor ID: " + professorId + ", Found: " + subjects.size());
        
        String[] subjectNames = new String[subjects.size()];
        
        for (int i = 0; i < subjects.size(); i++) {
            subjectNames[i] = subjects.get(i).getName() + " (" + subjects.get(i).getCode() + ")";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectNames);
        lvSubjects.setAdapter(adapter);

        lvSubjects.setOnItemClickListener((parent, view, position, id) -> {
            Subject selectedSubject = subjects.get(position);
            Intent intent = new Intent(this, MarksEntryActivity.class);
            intent.putExtra("subjectId", selectedSubject.getId());
            intent.putExtra("subjectName", selectedSubject.getName());
            intent.putExtra("professorId", professorId);
            intent.putExtra("professorName", getIntent().getStringExtra("userName"));
            startActivity(intent);
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (professorId != -1) {
            loadSubjects();
        }
    }
}