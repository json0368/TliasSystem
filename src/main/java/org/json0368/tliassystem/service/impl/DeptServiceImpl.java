package org.json0368.tliassystem.service.impl;

import org.json0368.tliassystem.controller.DeptController;
import org.json0368.tliassystem.mapper.DeptMapper;
import org.json0368.tliassystem.pojo.Dept;
import org.json0368.tliassystem.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    private static final Logger log = LoggerFactory.getLogger(DeptServiceImpl.class);

    @Override
    public List<Dept> findAll() {
        log.info("查询全部部门数据");
        return deptMapper.findAll();
    }

    @Override
    public Dept findById(int id) {
        log.info("查询 id 为 " + id + " 的部门数据");
        return deptMapper.findById(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        log.info("删除 id 为 " + id + " 的部门数据");
        return deptMapper.deleteById(id) > 0;
    }

    @Override
    public boolean add(Dept dept) {
        log.info("添加部门数据: " + dept);
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.add(dept) > 0;
    }

    @Override
    public boolean update(Dept dept) {
        log.info("更新部门数据: " + dept);
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.update(dept) > 0;
    }
}
