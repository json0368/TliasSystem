package org.json0368.tliassystem.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.json0368.tliassystem.pojo.EmpExpr;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    public void addBatch(List<EmpExpr> empExprList);

    @Select("select * from emp_expr where emp_id = #{empId}")
    public List<EmpExpr> list(Integer empId);

    @Delete("delete from emp_expr where emp_id = #{empId}")
    public void deleteByEmpId(Integer empId);
}
