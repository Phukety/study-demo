package com.phukety.demo.collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

public class Student {
    // 学生姓名
    private String name;
    // 学生的课程
    private List<String> courses;

    public Student(String name, List<String> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public static void main(String[] args) {
        // 初始化学生课程
        Student student = new Student("张三", new ArrayList<String>() {{
            add("数学");
            add("语文");
            add("英语");
        }});
        System.out.println("当前课程数量为: " + student.getCourses().size());

        // 如果想要添加其它课程，则可以获取student的课程对象，进行添加
        List<String> courseList = student.getCourses();
        // 继续添加另外的课程
        courseList.add("物理");
        courseList.add("化学");
        System.out.println("当前课程数量为: " + student.getCourses().size());

        // 是否可能存在问题？
        // 如何解决？
    }
}
