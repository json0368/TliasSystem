package org.json0368.tliassystem.controller;

import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.json0368.tliassystem.pojo.Emp;
import org.json0368.tliassystem.pojo.EmpQueryParam;
import org.json0368.tliassystem.pojo.PageResult;
import org.json0368.tliassystem.pojo.Result;
import org.json0368.tliassystem.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// @Slf4j 自动为类生成一个名为 log 的日志记录器（Logger）对象
@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result listByPage(EmpQueryParam eqp) {
        log.info("查询第 " + eqp.getPage() + " 页的员工数据， 每页 " + eqp.getPageSize() + " 条");
        log.info("查询条件： name = " + eqp.getName() + ", gender = " + eqp.getGender() +
                ", begin = " + eqp.getBegin() + ", end = " + eqp.getEnd());
        PageResult<Emp> pageResult = empService.findByPage(eqp);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除 id 为 " + ids + " 的员工数据");
        boolean flag = empService.deleteByIds(ids);
        if (!flag) {
            return Result.error("删除失败，请检查是否存在 ids 为 " + ids + " 的员工数据");
        } else {
            return Result.success();
        }
    }

    @PostMapping
    public Result add(@RequestBody Emp emp) {
        log.info("添加员工数据: " + emp);
        empService.add(emp);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        log.info("查询 id 为 " + id + " 的员工数据");
        Emp emp = empService.findById(id);
        if (emp != null) {
            return Result.success(emp);
        } else {
            return Result.error("查询失败，请检查是否存在 id 为 " + id + " 的员工数据");
        }
    }

    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("更新员工数据: " + emp);
        boolean flag = empService.update(emp);
        if (!flag) {
            return Result.error("更新失败，请检查是否存在 id 为 " + emp.getId() + " 的员工数据");
        } else {
            return Result.success();
        }
    }

    @GetMapping("/list")
    public Result list() {
        log.info("查询全部员工数据");
        List<Emp> empList = empService.findAll();
        return Result.success(empList);
    }
}
