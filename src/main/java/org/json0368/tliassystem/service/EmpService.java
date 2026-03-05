package org.json0368.tliassystem.service;

import org.json0368.tliassystem.pojo.Emp;
import org.json0368.tliassystem.pojo.EmpQueryParam;
import org.json0368.tliassystem.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {

    PageResult<Emp> findByPage(EmpQueryParam eqp);

    boolean deleteByIds(List<Integer> ids);

    void add(Emp emp);

    Emp findById(Integer id);

    boolean update(Emp emp);

    List<Emp> findAll();
}
