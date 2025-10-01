package com.example.mad_project.models;

public class Subject {
    private int id;
    private String name;
    private String code;
    private int professorId;

    public Subject() {}

    public Subject(int id, String name, String code, int professorId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.professorId = professorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public int getProfessorId() { return professorId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }
}