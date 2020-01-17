package connect.util;

import connect.entity.DataProperties;
import connect.pool.JdbcPool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取数据list或者操作
 *
 * @Author YangShun
 * @Date 2020/1/13 18:07
 */
public class JdbcUtil<T> {
    private JdbcPool Pool = null;
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    private JdbcPool jdbcPool = new JdbcPool();
    private DataProperties dataProperties;

    public JdbcUtil(DataProperties dataProperties) {
        this.dataProperties = dataProperties;
    }

    public List<Object[]> select() {
        String sql = dataProperties.getSql();
        Integer num = dataProperties.getCols().length;
        List<Object[]> list = new ArrayList<>();
        connection = jdbcPool.getCurrentConnection();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Object[] objects = new Object[num];
                for (int i = 0; i < num; i++) {
                    Object object = resultSet.getObject(i + 1);
                    objects[i] = object;
                }
                list.add(objects);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcPool.release(connection);
            jdbcPool.close(statement, resultSet);
        }

        return list;
    }

    public void delete() {
        String sql = dataProperties.getSql();
        connection = jdbcPool.getCurrentConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcPool.release(connection);
            jdbcPool.close(statement, resultSet);
        }
    }

    public void update(T t) {
        String sql = dataProperties.getSql();
        String[] col = dataProperties.getCols();
        connection = jdbcPool.getCurrentConnection();
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < col.length; i++) {
                StringBuffer col1 = new StringBuffer();
                col1.append("get");
                String substring = col[i].substring(0, 1).toUpperCase();
                col1.append(substring);
                col1.append(col[i].substring(1, col[i].length()));
                try {
                    Method declaredMethod = dataProperties.getClazz().getDeclaredMethod(col1.toString());
                    Object invoke = declaredMethod.invoke(t);
                    statement.setObject(i + 1, invoke);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            Method getId_ = dataProperties.getClazz().getDeclaredMethod("getId_");
            Object invoke = getId_.invoke(t);
            statement.setObject(col.length + 1, invoke);
            statement.executeUpdate();
        } catch (SQLException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            jdbcPool.release(connection);
            jdbcPool.close(statement, resultSet);
        }
    }

    public void insert(T t) {
        String sql = dataProperties.getSql();
        String[] cols = dataProperties.getCols();
        connection = jdbcPool.getCurrentConnection();
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 1; i < cols.length; i++) {
                StringBuffer col1 = new StringBuffer();
                col1.append("get");
                String substring = cols[i].substring(0, 1).toUpperCase();
                col1.append(substring);
                col1.append(cols[i].substring(1, cols[i].length()));
                try {
                    Method declaredMethod = dataProperties.getClazz().getDeclaredMethod(col1.toString());
                    Object invoke = declaredMethod.invoke(t);
                    statement.setObject(i , invoke);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcPool.release(connection);
            jdbcPool.close(statement, resultSet);
        }
    }

    public void insert(List<T> list){
        String sql = dataProperties.getSql();
        String[] cols = dataProperties.getCols();
        connection=jdbcPool.getCurrentConnection();
        try {
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            for (T t : list) {
                connection.setAutoCommit(false);
                for (int i = 1; i < cols.length; i++) {
                    StringBuffer col1 = new StringBuffer();
                    col1.append("get");
                    String substring = cols[i].substring(0, 1).toUpperCase();
                    col1.append(substring);
                    col1.append(cols[i].substring(1, cols[i].length()));
                    try {
                        Method declaredMethod = dataProperties.getClazz().getDeclaredMethod(col1.toString());
                        Object invoke = declaredMethod.invoke(t);
                        statement.setObject(i, invoke);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcPool.release(connection);
            jdbcPool.close(statement, resultSet);
        }
    }

}
