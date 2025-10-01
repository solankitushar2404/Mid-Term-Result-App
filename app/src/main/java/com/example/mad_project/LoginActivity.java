package com.example.mad_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mad_project.database.DatabaseHelper;
import com.example.mad_project.models.User;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private RadioGroup rgRole;
    private Button btnLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        rgRole = findViewById(R.id.rgRole);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);

        // Database will be created with initial data if not exists

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = dbHelper.authenticateUser(username, password);
            if (user != null) {
                String selectedRole = rgRole.getCheckedRadioButtonId() == R.id.rbProfessor ? "PROFESSOR" : "STUDENT";
                
                if (user.getRole().equals(selectedRole)) {
                    Intent intent;
                    if (user.getRole().equals("PROFESSOR")) {
                        intent = new Intent(this, ProfessorDashboardActivity.class);
                        intent.putExtra("userId", user.getId());
                        intent.putExtra("userName", user.getName());
                    } else {
                        intent = new Intent(this, StudentDashboardActivity.class);
                        intent.putExtra("userId", user.getId());
                        intent.putExtra("userName", user.getName());
                        intent.putExtra("enrollmentNo", user.getUsername());
                    }
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid role selection", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}