package com.example.mad_project.models;

public class Result {
    private int id;
    private int studentId;
    private int subjectId;
    private float marks;

    public Result() {}

    public Result(int id, int studentId, int subjectId, float marks) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marks = marks;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    public float getMarks() { return marks; }
    public void setMarks(float marks) { this.marks = marks; }
}