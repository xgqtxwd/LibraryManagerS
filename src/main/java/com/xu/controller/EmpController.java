//package com.xu.controller;
//
//import com.xu.pojo.Result;
//import com.xu.pojo.pageBean;
//import com.xu.service.EmpService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Slf4j
//@RestController
//public class EmpController {
//    @Autowired
//    private EmpService empService;
//
//    @GetMapping("/emps")
//    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize, String name
//    , Short gender, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end) {
//        log.info("分页查询：" + page + "," + pageSize+","+name+","+gender+","+begin+","+end);
//        pageBean pageBean = empService.page(page,pageSize,name,gender, begin,end);
//        return Result.success(pageBean);
//
//
//    }
//    @DeleteMapping("/emps/{ids}")
//    public Result deleteByIds(  List<Integer> ids){
//        log.info("批量删除，ids{}",ids);
//        empService.deleteByIds(ids);
//        return  Result.success();
//    }
//    //新增员工
//    @PostMapping("/emps")
//    public Result save(@RequestBody Emp emp){
//        log.info("新增员工，emp{}",emp.toString());
//        empService.addEmp(emp);
//        return Result.success();
//    }
//    @GetMapping("/emps/{id}")
//    public Result getEmp(@PathVariable Integer id){
//        log.info("查询员工信息id{}",id);
//        Emp emp=empService.get(id);
//     return Result.success(emp);
//    }
//    @PutMapping("/emps")
//    public Result update(@RequestBody Emp emp){
//        log.info("修改员工信息emp{}",emp.toString());
//        empService.update(emp);
//        return Result.success();
//    }
//}
