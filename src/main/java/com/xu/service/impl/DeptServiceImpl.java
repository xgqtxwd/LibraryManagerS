//package com.xu.service.impl;
//
//import com.xu.mapper.DeptMapper;
//import com.xu.mapper.EmpMapper;
//import com.xu.service.DeptService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//@Service
//public class DeptServiceImpl implements DeptService {
//    @Autowired
//private DeptMapper deptMapper;
//    @Autowired
//private EmpMapper empMapper;
//    @Override
//    public List<Dept> list() {
//        return deptMapper.list();
//
//    }
//
//@Override
//@Transactional(rollbackFor = Exception.class)
//public void delete(Integer id) {
//        deptMapper.deleteByid(id);
//        empMapper.deleteByDeptId(id);
//
//    }
//    @Override
//    public void add(Dept dept){
//        dept.setCreateTime(LocalDateTime.now());
//        dept.setUpdateTime(LocalDateTime.now());
//        deptMapper.insert(dept);
//    }
//
//    @Override
//    public void updateByid(Dept dept) {
//        deptMapper.updateByID(dept);
//
//    }
//
//    @Override
//    public Dept getByid(Integer id) {
//    return deptMapper.getById(id);
//    }
//
//}
