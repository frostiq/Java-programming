package Bazhanau.Task2.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentModel implements Comparable<StudentModel>, Serializable {
    private String surname;
    public static final int DEFAULT_GROUP = -1;
    private int group = DEFAULT_GROUP;
    private static final int marksCount = 3;
    private ArrayList<Integer> marks = new ArrayList<>(marksCount);

    public StudentModel() {
        for (int i = 0; i < marksCount; i++) {
            marks.add(-1);
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getGroupId() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public static int getMarksCount() {
        return marksCount;
    }

    public ArrayList<Integer> getMarks() {
        return marks;
    }

    public double getAverage() {
        double sum = 0;
        for (Integer mark : marks) {
            sum += mark;
        }
        return sum / marks.size();
    }

    @Override
    public int compareTo(StudentModel o) {
        if (this.getSurname() != null && o.getSurname() != null)
            return this.getSurname().compareTo(o.getSurname());
        else
            return 0;
    }
}
