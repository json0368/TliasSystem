package org.json0368.tliassystem.mapper;

import org.apache.ibatis.annotations.*;
import org.json0368.tliassystem.pojo.Dept;

import java.util.List;

@Mapper
public interface DeptMapper {

    // 查询全部部门数据，并按照更新时间降序排序

//    // 在 MyBatis 中，如果数据库列名与 Java 对象属性名不一致，可以通过以下几种方式来解决这个问题：
//    // 方法一： 手动结果映射：@Results 注解用于指定数据库列与 Java 对象属性之间的映射关系
//    @Results({
//            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "update_time", property = "updateTime")
//    })
//    @Select("select * from dept order by update_time desc")

//    // 方法二： 起别名：在 SQL 查询中使用 AS 关键字为列起别名，使其与 Java 对象属性名称一致
//    @Select("select id, name, create_time as createTime, update_time as updateTime from dept order by update_time desc")

    // 方法三： 全局配置：在 MyBatis 的全局配置文件中设置 mapUnderscoreToCamelCase=true，启用下划线到驼峰命名的自动映射功能(xxx_abc -> xxxAbc)
    @Select("select * from dept order by update_time desc")
    public List<Dept> findAll();

    // 根据 id 查询部门数据
    @Select("select * from dept where id = #{id}")
    public Dept findById(int id);

    // 根据 id 删除部门数据
    @Delete("delete from dept where id = #{id}")
    public int deleteById(Integer id);

    // 添加部门
    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int add(Dept dept);

    // 更新部门数据
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    public int update(Dept dept);
}
