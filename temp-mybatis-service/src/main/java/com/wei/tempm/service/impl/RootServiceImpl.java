package com.wei.tempm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wei.tempm.mapper.RootMapper;
import com.wei.tempm.service.PageBean;
import com.wei.tempm.service.RootService;

@Service
public abstract class RootServiceImpl<T>
	implements RootService<T> {

protected abstract RootMapper<T> getDao();

@Override
public List<T> list(Map<String, Object> p) {
	return getDao().list(p);
}

@Override
public PageBean<T> listPage(int pageNo, int pageSize, Map<String, Object> p) {
	Page<?> page = PageHelper.startPage(pageNo, pageSize);

	List<T> l = getDao().list(p);

	PageBean<T> r = new PageBean<T>();
	r.setAllPages(page.getPages());
	r.setPageSize(page.getPageSize());
	r.setAllRows(page.getTotal());
	r.setPageNo(page.getPageNum());
	r.setLst(l);
	return r;
}

@Override
public T getById(long id) {
	return getDao().getById(id);
}

@Override
public T get(Map<String, Object> p) {
	return getDao().get(p);
}

@Override
@Transactional
public long insert(T data) {
	return getDao().insert(data);
}

@Override
@Transactional
public long smartAdd(T data) {
	List<T> lst = new ArrayList<>();
	lst.add(data);
	return getDao().insertBatch(lst);
}

@Override
@Transactional
public long insertBatch(List<T> lst) {
	return getDao().insertBatch(lst);
}

@Override
@Transactional
public long delById(long id) {
	return getDao().delById(id);
}

@Override
@Transactional
public long del(Map<String, Object> p) {
	return getDao().del(p);
}

@Override
@Transactional
public long update(T data) {
	return getDao().update(data);
}
}
