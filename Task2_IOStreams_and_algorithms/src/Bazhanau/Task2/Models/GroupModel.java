package Bazhanau.Task2.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupModel implements Serializable, Comparable<GroupModel> {
    private int id;

    private ArrayList<StudentModel> students = new ArrayList<>();

    public int getId() {
        return id;
    }

    public GroupModel(int id) {
        this.id = id;
    }

    public GroupModel addStudent(StudentModel studentModel) {
        students.add(studentModel);
        return this;
    }

    public ArrayList<StudentModel> getStudents() {
        return students;
    }

    @Override
    public int compareTo(GroupModel o) {
        return Double.compare(o.getGroupAverage(), this.getGroupAverage());
    }

    public double getGroupAverage() {
        double res = 0;
        for (StudentModel student : students) {
            res += student.getAverage();
        }

        return res / students.size();
    }
}
