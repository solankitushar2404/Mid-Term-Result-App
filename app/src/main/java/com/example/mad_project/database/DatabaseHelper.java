package com.example.mad_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mad_project.models.User;
import com.example.mad_project.models.Subject;
import com.example.mad_project.models.Result;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "midterm_results.db";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT UNIQUE, password TEXT, role TEXT, name TEXT)");
        db.execSQL("CREATE TABLE subjects (id INTEGER PRIMARY KEY, name TEXT, code TEXT, professor_id INTEGER)");
        db.execSQL("CREATE TABLE enrollments (id INTEGER PRIMARY KEY, student_id INTEGER, subject_id INTEGER)");
        db.execSQL("CREATE TABLE results (id INTEGER PRIMARY KEY, student_id INTEGER, subject_id INTEGER, marks REAL)");
        
        preloadData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS subjects");
        db.execSQL("DROP TABLE IF EXISTS enrollments");
        db.execSQL("DROP TABLE IF EXISTS results");
        onCreate(db);
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM users");
        db.execSQL("DELETE FROM subjects");
        db.execSQL("DELETE FROM enrollments");
        db.execSQL("DELETE FROM results");
        preloadData(db);
    }
    
    public float getStudentMarks(int studentId, int subjectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("results", new String[]{"marks"}, 
                "student_id=? AND subject_id=?", 
                new String[]{String.valueOf(studentId), String.valueOf(subjectId)}, 
                null, null, null);
        
        float marks = 0;
        if (cursor.moveToFirst()) {
            marks = cursor.getFloat(0);
        }
        cursor.close();
        return marks;
    }

    private void preloadData(SQLiteDatabase db) {
        // Demo Professor Account
        db.execSQL("INSERT INTO users VALUES (1, 'prof.vyas', 'pass123', 'PROFESSOR', 'Prof. C.A. Vyas')");
        
        // Demo Student Accounts (Enrollment format)
        db.execSQL("INSERT INTO users VALUES (2, '220210107067', '107067', 'STUDENT', 'Demo Student 1')");
        db.execSQL("INSERT INTO users VALUES (3, '220210107003', '107003', 'STUDENT', 'Demo Student 2')");
        db.execSQL("INSERT INTO users VALUES (4, '220210107074', '107074', 'STUDENT', 'Demo Student 3')");
        db.execSQL("INSERT INTO users VALUES (5, '220210107004', '107004', 'STUDENT', 'Demo Student 4')");
        db.execSQL("INSERT INTO users VALUES (6, '220210107053', '107053', 'STUDENT', 'Demo Student 5')");
        
        // Demo Subjects
        db.execSQL("INSERT INTO subjects VALUES (1, 'Mobile Application Development', 'MAD101', 1)");
        db.execSQL("INSERT INTO subjects VALUES (2, 'Computer Networks', 'CN101', 1)");
        
        // Demo Enrollments
        db.execSQL("INSERT INTO enrollments VALUES (1, 2, 1)");
        db.execSQL("INSERT INTO enrollments VALUES (2, 3, 1)");
        db.execSQL("INSERT INTO enrollments VALUES (3, 4, 1)");
        db.execSQL("INSERT INTO enrollments VALUES (4, 5, 1)");
        db.execSQL("INSERT INTO enrollments VALUES (5, 6, 1)");
        db.execSQL("INSERT INTO enrollments VALUES (6, 2, 2)");
        db.execSQL("INSERT INTO enrollments VALUES (7, 3, 2)");
        db.execSQL("INSERT INTO enrollments VALUES (8, 4, 2)");
        db.execSQL("INSERT INTO enrollments VALUES (9, 5, 2)");
    }

    public User authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Debug: Check if user exists
        Cursor debugCursor = db.query("users", null, "username=?", 
                new String[]{username}, null, null, null);
        if (debugCursor.moveToFirst()) {
            String dbPassword = debugCursor.getString(2);
            android.util.Log.d("AUTH", "User found. Expected: " + dbPassword + ", Got: " + password);
        } else {
            android.util.Log.d("AUTH", "User not found: " + username);
        }
        debugCursor.close();
        
        Cursor cursor = db.query("users", null, "username=? AND password=?", 
                new String[]{username, password}, null, null, null);
        
        if (cursor.moveToFirst()) {
            User user = new User(cursor.getInt(0), cursor.getString(1), 
                    cursor.getString(2), cursor.getString(3), cursor.getString(4));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    public List<Subject> getSubjectsByProfessor(int professorId) {
        List<Subject> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("subjects", null, "professor_id=?", 
                new String[]{String.valueOf(professorId)}, null, null, null);
        
        while (cursor.moveToNext()) {
            subjects.add(new Subject(cursor.getInt(0), cursor.getString(1), 
                    cursor.getString(2), cursor.getInt(3)));
        }
        cursor.close();
        return subjects;
    }

    public List<User> getStudentsBySubject(int subjectId) {
        List<User> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT u.* FROM users u JOIN enrollments e ON u.id = e.student_id WHERE e.subject_id = ?",
                new String[]{String.valueOf(subjectId)});
        
        while (cursor.moveToNext()) {
            students.add(new User(cursor.getInt(0), cursor.getString(1), 
                    cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }
        cursor.close();
        return students;
    }

    public void saveResult(int studentId, int subjectId, float marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("student_id", studentId);
        values.put("subject_id", subjectId);
        values.put("marks", marks);
        
        android.util.Log.d("DB_SAVE", "Saving marks: Student=" + studentId + ", Subject=" + subjectId + ", Marks=" + marks);
        
        Cursor cursor = db.query("results", null, "student_id=? AND subject_id=?",
                new String[]{String.valueOf(studentId), String.valueOf(subjectId)}, null, null, null);
        
        if (cursor.moveToFirst()) {
            int updated = db.update("results", values, "student_id=? AND subject_id=?",
                    new String[]{String.valueOf(studentId), String.valueOf(subjectId)});
            android.util.Log.d("DB_SAVE", "Updated " + updated + " rows");
        } else {
            long inserted = db.insert("results", null, values);
            android.util.Log.d("DB_SAVE", "Inserted row ID: " + inserted);
        }
        cursor.close();
    }

    public List<Result> getResultsByStudent(int studentId) {
        List<Result> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("results", null, "student_id=?", 
                new String[]{String.valueOf(studentId)}, null, null, null);
        
        android.util.Log.d("DB_READ", "Getting results for student: " + studentId);
        
        while (cursor.moveToNext()) {
            Result result = new Result(cursor.getInt(0), cursor.getInt(1), 
                    cursor.getInt(2), cursor.getFloat(3));
            results.add(result);
            android.util.Log.d("DB_READ", "Found result: " + result.getMarks() + " for subject " + result.getSubjectId());
        }
        cursor.close();
        android.util.Log.d("DB_READ", "Total results found: " + results.size());
        return results;
    }

    public List<Subject> getSubjectsByStudent(int studentId) {
        List<Subject> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT s.* FROM subjects s JOIN enrollments e ON s.id = e.subject_id WHERE e.student_id = ?",
                new String[]{String.valueOf(studentId)});
        
        while (cursor.moveToNext()) {
            subjects.add(new Subject(cursor.getInt(0), cursor.getString(1), 
                    cursor.getString(2), cursor.getInt(3)));
        }
        cursor.close();
        return subjects;
    }

    public boolean hasResult(int studentId, int subjectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("results", null, "student_id=? AND subject_id=?",
                new String[]{String.valueOf(studentId), String.valueOf(subjectId)}, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}