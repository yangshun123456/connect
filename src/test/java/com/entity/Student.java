package com.entity;

/**
 * @Author YangShun
 * @Date 2020/1/16 11:27
 */
public class Student {
    private Integer id_;
    private String name;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public Integer getId_() {
        return id_;
    }

    public void setId_(Integer id_) {
        this.id_ = id_;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id_=" + id_ +
                ", name='" + name + '\'' +
                '}';
    }
}
