package com.anapp.tpb.replacement.Storage.TableTemplates;

import java.io.Serializable;

/**
 * Created by Theo on 19/02/2016.
 */
public class ClassTime implements Serializable, Comparable<ClassTime> {
    private int id;
    private int day;
    private int subjectID;
    private int start;
    private int end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    @Override
    public int compareTo (ClassTime another) {
        if(start >= another.getEnd()) {
            return 1;
        } else {
            return -1;
        }
    }

    public boolean overlaps(ClassTime toCheck) {
        return (start < toCheck.getEnd() && end > toCheck.getStart());
    }

    @Override
    public String toString() {
        return "Class {id=" + id + ", SubjectId=" + subjectID + ", start=" + start + ", end=" + end + ", day=" + day + "}";
    }

    @Override
    public boolean equals(Object o) {
        try {
            ClassTime ct = (ClassTime) o;
            return id == ct.getId();
        } catch (Exception e) {
            return false;
        }
    }
}
