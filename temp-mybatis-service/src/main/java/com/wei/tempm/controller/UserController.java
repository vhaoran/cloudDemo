package com.wei.tempm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wei.tempm.entity.User;
import com.wei.tempm.service.PageBean;
import com.wei.tempm.service.UserService;

@EnableAutoConfiguration
@RestController
public class UserController {

@Autowired
private UserService obj;

// ----------------query----------------
// get,通过主键查询单值
@RequestMapping(value = "/User/{id}", method = RequestMethod.GET)
public User get(@PathVariable("id") Long id) {
	return obj.getById(id);
}

// ----------------query----------------
// get,复杂条件，查询单值
@RequestMapping(value = "/User/get", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public User getMap(@RequestBody Map<String, Object> p) {
	return obj.get(p);
}

// ----------------query----------------
// list--post，查询集合，复杂条件
@RequestMapping(value = "/User/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public List<User> list(@RequestBody Map<String, Object> p) {
	return obj.list(p);
}

// ----------------query----------------
// listPage 分页查询集合，复杂条件
@RequestMapping(value = "/User/listPage/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public PageBean<User> listPage(
	@PathVariable("pageNo") int pageNo,
	@PathVariable("pageSize") int pageSize,
	@RequestBody Map<String, Object> p) {
	return obj.listPage(pageNo, pageSize, p);
}

// ---------------add or update-------------------------------------
// put add or update,幂等操作，用于新增或修改数据
@RequestMapping(value = "/User", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
public Long smartAdd(@RequestBody User entity) {
	return obj.smartAdd(entity);
}

// --------add-----update--------------------
// put add or update,幂等操作，用于新增或修改数据
@RequestMapping(value = "/User/batch", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
public Long insertBatch(@RequestBody List<User> lst) {
	return obj.insertBatch(lst);
}

// ---------------del-----------------------------
@RequestMapping(value = "/User/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
public Long delByid(@PathVariable Long id) {
	return obj.delById(id);
}

// ---------------del-----------
// del by map
@RequestMapping(value = "/User", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
public Long del(@RequestBody Map<String, Object> p) {
	return obj.del(p);
}

// ---------------update-----------
// update entity
@RequestMapping(value = "/User/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public Long update(@RequestBody User entity) {
	return obj.update(entity);
}

}
