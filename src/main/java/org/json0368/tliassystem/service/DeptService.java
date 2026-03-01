package org.json0368.tliassystem.service;

import org.json0368.tliassystem.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAll();

    Dept findById(int id);

    boolean deleteById(Integer id);

    boolean add(Dept dept);

    boolean update(Dept dept);
}
