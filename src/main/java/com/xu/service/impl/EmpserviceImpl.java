//package com.xu.service.impl;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.xu.mapper.EmpMapper;
//import com.xu.pojo.pageBean;
//import com.xu.service.EmpService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class EmpserviceImpl implements EmpService {
//    @Autowired
//    private EmpMapper empMapper;
//
//    @Override
//    public Emp login(Emp emp) {
//        return empMapper.getBypassword(emp);
//    }
//
//    @Override
////    public pageBean page(int page, int pageSize) {
////        Long count = empMapper.count();
////        int start = (page - 1) * pageSize;
////        List<Emp> empList = empMapper.page(start, pageSize);
////         pageBean pageBean = new pageBean(count,empList);
////         return pageBean;
////
////    }
//    public pageBean page(Integer page, Integer pageSize,String name, Short gender,LocalDate begin, LocalDate end) {
//       //设置分页参数
//        PageHelper.startPage(page, pageSize);
//        //执行查询
//        List<Emp> empList = empMapper.list(name, gender,begin,end);
//        Page<Emp> empPage = (Page<Emp>)empList;
//        pageBean pageBean = new pageBean(empPage.getTotal(),empPage.getResult());
//        return pageBean;
//    }
//
//    @Override
//    public void deleteByIds(List<Integer> ids) {
//        empMapper.deleteByIds(ids);
//    }
//
//    @Override
//    public void addEmp(Emp emp) {
//        emp.setUpdateTime(LocalDateTime.now());
//        emp.setCreateTime(LocalDateTime.now());
//        empMapper.add(emp);
//    }
//
//    @Override
//    public void update(@RequestBody Emp emp) {
//        emp.setUpdateTime(LocalDateTime.now());
//        empMapper.update(emp);
//    }
//
//
//    @Override
//    public Emp get(Integer id) {
//      Emp emp= empMapper.get(id);
//      return emp;
//    }
//}
