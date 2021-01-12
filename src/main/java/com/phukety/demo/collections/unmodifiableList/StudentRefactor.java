package com.phukety.demo.collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentRefactor {
    // 学生姓名
    private String name;
    // 学生的课程
    private final List<String> courses;

    public StudentRefactor(String name, List<String> courses) {
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
        // 获取只读集合,若对该集合执行修改操作，会报UnsupportedOperationException异常
        return Collections.unmodifiableList(courses);
    }

    // 提供唯一添加课程的入口，专一职责
    public void addCourses(String course) {
        this.courses.add(course);
    }

    public static void main(String[] args) {
        // 初始化学生课程
        StudentRefactor student = new StudentRefactor("张三", new ArrayList<String>() {{
            add("数学");
            add("语文");
            add("英语");
        }});
        System.out.println("当前课程数量为: " + student.getCourses().size());

        // 继续添加另外的课程
        student.addCourses("物理");
        student.addCourses("化学");
        System.out.println("当前课程数量为: " + student.getCourses().size());
    }
}
