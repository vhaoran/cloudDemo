package com.wei.tempm.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.wei.tempm.entity.User;

public interface RootService<T> {

// 查询
List<T> list(Map<String, Object> p);

PageBean<T> listPage(int pageNo, int rows, Map<String, Object> p);

// 查询ID
T getById(long id);

T get(Map<String, Object> p);

// 修改
long update(T data);

// 添加
long insert(T data);

long smartAdd(T data);

long insertBatch(List<T> lst);

// 删除
long delById(long id);

long del(Map<String, Object> p);

}
