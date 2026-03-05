package org.json0368.tliassystem.mapper;

import org.apache.ibatis.annotations.*;
import org.json0368.tliassystem.pojo.Emp;
import org.json0368.tliassystem.pojo.EmpExpr;
import org.json0368.tliassystem.pojo.EmpQueryParam;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
    // 原始方法实现分页查询遗留代码
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public int count();

    public List<Emp> findByPage(EmpQueryParam eqp);

    public int deleteByIds(List<Integer> ids);

    public void add(Emp emp);

    @Select("select e.*, d.name as dept_name from emp e left join dept d on e.dept_id = d.id where e.id = #{id}")
    public Emp findById(Integer id);

    public int update(Emp emp);

    public List<Emp> findAll();
}

