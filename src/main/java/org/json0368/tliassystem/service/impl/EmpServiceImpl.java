package org.json0368.tliassystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.json0368.tliassystem.mapper.EmpExprMapper;
import org.json0368.tliassystem.mapper.EmpMapper;
import org.json0368.tliassystem.pojo.*;
import org.json0368.tliassystem.service.EmpLogService;
import org.json0368.tliassystem.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    // 原始方法实现分页查询
//    @Override
//    public PageResult<Emp> findByPage(Integer page, Integer pageSize) {
//        Integer total = empMapper.count();
//        List<Emp> rows = empMapper.findByPage((page - 1) * pageSize, pageSize);
//        PageResult pageResult = new PageResult(total, rows);
//        return pageResult;
//    }

    // 使用 PageHelper 实现分页查询
    // 注意事项：
    // 1. 使用 PageHelper 方法时， mapper 的 sql 普通查询方法不能加分号
    // 2. PageHelper 仅会拦截紧跟在 PageHelper.startPage() 方法之后的第一个查询方法
    @Override
    public PageResult<Emp> findByPage(EmpQueryParam eqp) {
        PageHelper.startPage(eqp.getPage(), eqp.getPageSize());
        // empList 实际上是一个 Page 对象，因为 PageHelper.startPage() 方法会拦截后续的查询，并将查询结果封装成一个 Page 对象返回
        // 由于 Page 类继承了 ArrayList，所以 empList 仍然可以被当作一个普通的 List 来使用
        List<Emp> empList = empMapper.findByPage(eqp);
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>((int) p.getTotal(), p.getResult());
    }

    // @Transactional 注解表示该方法需要在一个事务中执行，如果方法执行过程中发生异常，事务会自动回滚，保证数据的一致性和完整性
    // 该注解用于业务层的方法上，类上或者接口上
    // rollbackFor 属性指定哪些异常会导致事务回滚，默认情况下，只有 RuntimeException 和 Error 会导致事务回滚，如果需要让其他类型的异常也能导致事务回滚，可以通过 rollbackFor 属性来指定
    // propagation 属性指定事务的传播行为，默认值为 Propagation.REQUIRED，表示如果当前存在一个事务，则加入该事务；如果当前没有事务，则创建一个新的事务；如果当前存在一个事务，但该方法被设置为 Propagation.REQUIRES_NEW，则会挂起当前事务，并创建一个新的事务来执行该方法；如果当前存在一个事务，但该方法被设置为 Propagation.NESTED，则会在当前事务中创建一个嵌套事务来执行该方法
    @Transactional(rollbackFor = Exception.class )
    @Override
    public boolean deleteByIds(List<Integer> ids) {
        Integer flag = empMapper.deleteByIds(ids);
        if (flag <= 0) {
            return false;
        }
        for (Integer id : ids) {
            empExprMapper.deleteByEmpId(id);
        }
        return true;
    }

    @Transactional
    @Override
    public void add(Emp emp) {
        try{
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.add(emp);
            Integer empId = emp.getId();
            List<EmpExpr> exprList = emp.getExprList();
            if (exprList != null && exprList.size() > 0) {
                for (EmpExpr expr : exprList) {
                    expr.setEmpId(empId);
                }
                empExprMapper.addBatch(exprList);
            }
        } finally {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "添加了员工数据: " + emp);
            empLogService.insertLog(empLog);
        }
    }

    @Override
    public Emp findById(Integer id) {
        Emp emp = empMapper.findById(id);
        if (emp != null) {
            emp.setExprList(empExprMapper.list(id));
        }
        return emp;
    }

    @Transactional
    @Override
    public boolean update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        Integer flag = empMapper.update(emp);

        if (flag <= 0) {
            return false;
        }

        Integer empId = emp.getId();
        empExprMapper.deleteByEmpId(empId);
        List<EmpExpr> exprList = emp.getExprList();
        if (exprList != null && exprList.size() > 0) {
            for (EmpExpr expr : exprList) {
                expr.setEmpId(empId);
            }
            empExprMapper.addBatch(exprList);
        }
        return true;
    }

    @Override
    public List<Emp> findAll() {
        List<Emp> empList = empMapper.findAll();
        for (Emp emp : empList) {
            emp.setExprList(empExprMapper.list(emp.getId()));
        }
        return empList;
    }
}
