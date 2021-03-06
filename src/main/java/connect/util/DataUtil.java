package connect.util;

import connect.entity.DataProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于获取sql
 * id必须要用id_
 * @Author YangShun
 * @Date 2020/1/13 15:12
 */
public class DataUtil {
    private StringBuffer sql=new StringBuffer();
    private DataProperties dataProperties;

    public DataUtil(DataProperties dataProperties){
        this.dataProperties=dataProperties;
        //将字段与set方法一一对应
        dataProperties.setMethods(methods());
    }

    /**
     * 查询所有的数据sql
     * @return
     */
    public DataProperties findAll(){
        sql.append("select ");
        //拼接sql,需要查询的字段(select id,name )
        sql.append(cols());
        //拼接表名称
        sql.append("from "+table());
        //将sql封装到DataProperties
        dataProperties.setSql(sql.toString());

        return dataProperties;
    }

    /**
     * 查询单条数据sql
     * @param id
     * @return
     */
    public DataProperties get(Integer id){
        sql.append("select ");
        sql.append(cols());
        sql.append("from "+table());
        sql.append("where id_="+id);
        dataProperties.setSql(sql.toString());
        return dataProperties ;
    }

    /**
     * 删除单条数据sql
     * @param id
     * @return
     */
    public DataProperties delete(Integer id){
        sql.append("delete ");
        sql.append("from "+table());
        sql.append("where id_="+id);
        dataProperties.setSql(sql.toString());
        return dataProperties;
    }

    /**
     * 更新单条数据sql
     * @return
     */
    public DataProperties update(){
        sql.append("update ");
        sql.append(table()+"set ");
        for (int i=0;i<dataProperties.getCols().length;i++) {
            if(i!=dataProperties.getCols().length-1) {
                sql.append(dataProperties.getCols()[i] + "=?," + " ");
            }else {
                sql.append(dataProperties.getCols()[i] + "=?" + " ");
            }
        }
        sql.append("where id_=? ");
        dataProperties.setSql(sql.toString());
        return dataProperties;
    }

    /**
     * 新增单条数据sql
     * @return
     */
    public DataProperties insert(){
        String[] cols=dataProperties.getCols();
        sql.append("insert into ");
        sql.append(table()+"(");
        for (int i=1;i<cols.length;i++) {
            if(i!=cols.length-1){
                sql.append(cols[i]+",");
            }else{
                sql.append(cols[i]);
            }
        }
        sql.append(") ");
        sql.append("values(");
        for(int i=1;i<cols.length;i++){
            if(i!=cols.length-1){
                sql.append("?,");
            }else{
                sql.append("?)");
            }
        }
        dataProperties.setSql(sql.toString());
        return dataProperties;
    }

    /**
     * 获取table
     * @return
     */
    public String table(){
        String name = dataProperties.getClazz().getName();
        String[] split = name.split("\\.");
        String table = split[split.length - 1].toLowerCase();
        return table+" ";
    }

    /**
     * 拼接sql,需要查询的字段(select id,name )
     * @return
     */
    public String cols(){
        StringBuffer sql=new StringBuffer();
        for(int i=0;i<dataProperties.getCols().length;i++){
            if(i!=dataProperties.getCols().length-1) {
                sql.append(dataProperties.getCols()[i] + ",");
            }else{
                sql.append(dataProperties.getCols()[i]+" ");
            }
        }
        return sql.toString();
    }

    /**
     * 将字段与set方法一一对应
     * @return
     */
    public Map<String,String> methods(){
        Map<String,String> methods=new HashMap<String, String>();
        for (String col : dataProperties.getCols()) {
            StringBuffer col1=new StringBuffer();
            col1.append("set");
            String substring = col.substring(0, 1).toUpperCase();
            col1.append(substring);
            col1.append(col.substring(1,col.length()));
            methods.put(col,col1.toString());
        }
        return methods;
    }


}
