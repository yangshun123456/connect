package com.test;

import com.dao.StudentDao;
import com.entity.Student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangShun
 * @Date 2020/1/16 11:29
 */
public class Test {
    @org.junit.Test
    public void test(){
        StudentDao studentDao=new StudentDao();
        List<Student> all = studentDao.findAll();
        System.out.println(all);
    }

    @org.junit.Test
    public void test1(){
        Student student=new Student();
        student.setId_(1);
        student.setName("mike");
        StudentDao studentDao=new StudentDao();
        studentDao.update(student);
    }

    @org.junit.Test
    public void test2(){
        Student student=new Student();
        student.setName("mikdsae");
        StudentDao studentDao=new StudentDao();
        studentDao.insert(student);
    }

    @org.junit.Test
    public void test3(){
        List<Student> list=new ArrayList<>();
        Student student=new Student();
        student.setName("mikdsae");
        Student student1=new Student();
        student1.setName("m213dasae");
        Student student2=new Student();
        student2.setName("1231kdsae");
        list.add(student);
        list.add(student1);
        list.add(student2);
        StudentDao studentDao=new StudentDao();
        studentDao.insert(list);
    }
}
