package com.dao;

import com.entity.Student;
import connect.dao.BaseDao;

/**
 * @Author YangShun
 * @Date 2020/1/16 11:28
 */
public class StudentDao extends BaseDao<Student> {
    @Override
    public Class<Student> getClazz() {
        return Student.class;
    }

    @Override
    public String[] getCols() {
        return new String[]{"id_","name"};
    }
}
