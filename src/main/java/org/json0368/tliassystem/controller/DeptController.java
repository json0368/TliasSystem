package org.json0368.tliassystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json0368.tliassystem.service.DeptService;
import org.json0368.tliassystem.pojo.Dept;
import org.json0368.tliassystem.pojo.Result;

import java.time.LocalDateTime;
import java.util.List;

// 公共的请求路径前缀可以通过 @RequestMapping 注解来指定，这样就不需要在每个方法上重复指定路径了
@RequestMapping("/depts")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    // 1. 可以用 method 参数来指定请求方法
    // @RequestMapping("/depts", method = RequestMethod.GET)
    // 2. 也可以直接使用 @GetMapping 注解来简化代码
    @GetMapping
    public Result list() {
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        log.info("查询 id 为 " + id + " 的部门数据");
        Dept dept = deptService.findById(id);
        if (dept != null) {
            return Result.success(dept);
        } else {
            return Result.error("查询失败，不存在 id 为 " + id + " 的部门数据");
        }
    }

    // Controller 接受参数方法
    // 方法一：通过 HttpServletRequest 对象获取参数
//    @DeleteMapping
//    public Result delete(HttpServletRequest request) {
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//        System.out.println("删除 id 为 " + id + " 的部门数据");
//        boolean flag = deptService.deleteById(id);
//        if (flag) {
//            return Result.success();
//        } else {
//            return Result.error("删除失败，不存在 id 为 " + id + " 的部门数据");
//        }
//    }

    // 方法二：通过 @RequestParam 注解直接将请求参数绑定到方法参数上（推荐）
    // 注意，如果声明了 @RequestParam 注解，则必须提供参数值，否则会抛出异常（required 默认为 true）
    // 或者将 required 设置为 false，还可以提供一个默认值（defaultValue）
//    @DeleteMapping
//    public Result delete(@RequestParam(value = "id", required = false) Integer deptId) {
//        System.out.println("删除 id 为 " + deptId + " 的部门数据");
//        boolean flag = deptService.deleteById(deptId);
//        if (flag) {
//            return Result.success();
//        } else {
//            return Result.error("删除失败，不存在 id 为 " + deptId + " 的部门数据");
//        }
//    }

    // 方法三：当请求参数与方法参数名称一致时，可以省略 @RequestParam 注解，直接将请求参数绑定到方法参数上（推荐）
    @DeleteMapping
    public Result delete(Integer id) {
        log.info("删除 id 为 " + id + " 的部门数据");
        boolean flag = deptService.deleteById(id);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("删除失败，不存在 id 为 " + id + " 的部门数据");
        }
    }

    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("添加部门名称为 " + dept.getName() + " 的部门数据");
        boolean flag = deptService.add(dept);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("添加失败，请检查部门名称是否已存在");
        }
    }

    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("更新 id 为 " + dept.getId() + " 的部门数据");
        boolean flag = deptService.update(dept);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("更新失败，不存在 id 为 " + dept.getId() + " 的部门数据");
        }
    }
}
