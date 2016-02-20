package com.anapp.tpb.replacement.Storage.TableTemplates;

import java.io.Serializable;

/**
 * Created by Theo on 25/01/2016.
 */
public class Lesson implements Serializable {

    private int id;
    private String name;
    private String teacher;
    private String classroom;
    private int color;

    public Lesson() {
    }

    public Lesson(String name, String teacher, String classroom) {
        this.name = name;
        this.teacher = teacher;
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return "Lesson {id=" + id + ", name=" + name + ", teacher=" + teacher + ", classroom=" + classroom;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Lesson l = (Lesson) o;
            if (l.getId() == id) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}