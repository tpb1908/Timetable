package com.tpb.timetable.Data.Templates;

import java.io.Serializable;

/**
 * Created by theo on 27/05/16.
 */
public class Assessment implements Comparable<Assessment>, Serializable {
    private int id;
    private long date;
    private int subjectID;
    private Subject subject;
    private String revision;
    private int percentage;
    private boolean complete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Assessment) {
            Assessment a = (Assessment) o;
            return id == a.getId();
        }
        return false;
    }

    @Override
    public int compareTo(Assessment another) {
        if(date < another.getDate()) {
            return 1;
        } else if(date == another.getDate()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Assessment { id=" + id + ", date="+ date + ", subject=" + subjectID +
                ", revision=" + revision + ", percentage=" + percentage + ", complete="+complete;
    }
}
