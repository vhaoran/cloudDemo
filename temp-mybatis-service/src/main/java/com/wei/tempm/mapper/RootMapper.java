package com.wei.tempm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RootMapper<T> {

List<T> list(Map<String, Object> p);

T getById(@Param("id") long id);

T get(Map<String, Object> p);

long insert(T data);

long insertBatch(List<T> lst);

long delById(@Param("id") long id);

long del(Map<String, Object> p);

long update(T data);
}
