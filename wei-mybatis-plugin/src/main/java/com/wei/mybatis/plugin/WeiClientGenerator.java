package com.wei.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

public class WeiClientGenerator extends AbstractJavaGenerator {

private Properties properties;

private IntrospectedTable tb() {
	return this.getIntrospectedTable();
}

public WeiClientGenerator(Properties properties) {
	this.properties = properties;
}

private String beanName() {
	FullyQualifiedTable table = tb().getFullyQualifiedTable();
	String beanName = table.getDomainObjectName();
	return beanName;
}

private String mapperName() {
	// get MapperName
	String mapperName = beanName() + "Mapper";
	return mapperName;
}

private String pPackName() {
	// package name
	String mapperPackageName = context.getJavaModelGeneratorConfiguration()
		.getTargetPackage();
	String[] paths = mapperPackageName.split("\\.");
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < paths.length - 1; i++) {
		if (i == 0) {
			sb.append(paths[i]);
		} else if (i == paths.length - 1) {
			continue;
		} else {
			sb.append(".");
			sb.append(paths[i]);
		}
	}
	return sb.toString();

}

private String mapperPackName() {
	return pPackName() + ".mapper";
}

private String servicePackName() {
	return pPackName() + ".service";
}

private String beanPackName() {
	return pPackName() + ".entity";
}

private String serviceImplPackName() {
	return pPackName() + ".service.impl";
}

private String controllerPackName() {
	return pPackName() + ".controller";
}

@Override
public List<CompilationUnit> getCompilationUnits() {
	List<CompilationUnit> lst = new ArrayList<CompilationUnit>();
	// mapper files
	Interface mapperInterface = getMapper();
	lst.add(mapperInterface);

	// service files
	Interface serviceInterface = getService();
	lst.add(serviceInterface);

	// serviceImpl files
	TopLevelClass serviceImplClass = getServiceImpl();
	lst.add(serviceImplClass);
	// Controller files
	TopLevelClass controller = getController();
	lst.add(controller);

	// serviceImpl files
	return lst;
}

private TopLevelClass getController() {
	CommentGenerator comment = context.getCommentGenerator();
	//
	FullyQualifiedJavaType unitClass = new FullyQualifiedJavaType(
		controllerPackName() + "." + beanName() + "Controller");

	TopLevelClass topClass = new TopLevelClass(unitClass);
	topClass.setVisibility(JavaVisibility.PUBLIC);

	comment.addJavaFileComment(topClass);
	// 加入 @autowired
	// 加入 private UserService obj;

	// get
	Method get = controller_get();
	//comment.addGeneralMethodComment(get, introspectedTable);
	topClass.addMethod(get);

	// getMap
	Method getMap = controller_getMap();
	//comment.addGeneralMethodComment(getMap, introspectedTable);
	topClass.addMethod(getMap);

	// list
	Method list = controller_list();
	//comment.addGeneralMethodComment(list, introspectedTable);
	topClass.addMethod(list);

	// listPage
	Method listPage = controller_listPage();
	//comment.addGeneralMethodComment(listPage, introspectedTable);
	topClass.addMethod(listPage);

	//
	Field fd = new Field();
	fd.setName("obj");
	fd.setVisibility(JavaVisibility.PRIVATE);
	fd.setType(new FullyQualifiedJavaType(beanName() + "Service"));
	fd.addAnnotation("@Autowired");
	topClass.addField(fd);

	// smartAdd
	Method smartAdd = controller_smartAdd();
	//comment.addGeneralMethodComment(smartAdd, introspectedTable);
	topClass.addMethod(smartAdd);

	// insertBatch
	Method insertBatch = controller_insertBatch();
	//comment.addGeneralMethodComment(insertBatch, introspectedTable);
	topClass.addMethod(insertBatch);

	// insertBatch
	Method delById = controller_delById();
	//comment.addGeneralMethodComment(delById, introspectedTable);
	topClass.addMethod(delById);
	// insertBatch
	Method del = controller_del();
	//comment.addGeneralMethodComment(del, introspectedTable);
	topClass.addMethod(del);

	// update
	Method update = controller_update();
	//comment.addGeneralMethodComment(update, introspectedTable);
	topClass.addMethod(update);

	// -----------import-----------------
	topClass.addImportedType("java.util.List");
	topClass.addImportedType("java.util.Map");
	topClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");

	topClass
		.addImportedType("org.springframework.boot.autoconfigure.EnableAutoConfiguration");
	topClass.addImportedType("org.springframework.web.bind.annotation.PathVariable");
	topClass.addImportedType("org.springframework.web.bind.annotation.RequestBody");
	topClass.addImportedType("org.springframework.web.bind.annotation.RequestMapping");
	topClass.addImportedType("org.springframework.web.bind.annotation.RequestMethod");
	topClass.addImportedType("org.springframework.web.bind.annotation.RestController");
	topClass.addImportedType(pPackName() + ".common.PageBean");

	topClass.addImportedType(beanPackName() + "." + beanName());
	topClass.addImportedType(servicePackName() + "." + beanName() + "Service");

	// --------annonation-------------
	topClass.addAnnotation("@EnableAutoConfiguration");
	topClass.addAnnotation("@RestController");
	// topClass.addAnnotation("@RequestMapping(/" + beanName());

	return topClass;
}

private Method controller_update() {
	// ---------------update-----------
	// update entity
	// @RequestMapping(value = "/User/update", method = RequestMethod.POST, produces
	// = "application/json; charset=utf-8")
	// public Long update(@RequestBody User entity) {
	// return obj.update(entity);
	// }

	Method m = new Method();
	m.addJavaDocLine("// ---------------update-----------  ");
	m.addJavaDocLine("// update entity ");

	m.setVisibility(JavaVisibility.PUBLIC);
	m.setName("update");
	m.addAnnotation("@RequestMapping(value = \"/" + beanName()
		+ "/update\", method = RequestMethod.POST, produces = \"application/json; charset=utf-8\")");
	m.setReturnType(new FullyQualifiedJavaType("Long"));
	m.addParameter(
		new Parameter(new FullyQualifiedJavaType(beanName()),
			"entity",
			"@RequestBody"));
	m.addBodyLine("return obj.update(entity);");
	return m;
}

private Method controller_del() {
	// ---------------del-----------------------------
	// del by map
	// @RequestMapping(value = "/User", method = RequestMethod.DELETE, produces =
	// "application/json; charset=utf-8")
	// public Long del(@RequestBody Map<String, Object> p) {
	// return obj.del(p);
	// }
	Method m = new Method();
	m.addJavaDocLine("// ---------------del-----------  ");
	m.addJavaDocLine("// del by map  ");

	m.setVisibility(JavaVisibility.PUBLIC);
	m.setName("del");
	m.addAnnotation("@RequestMapping(value = \"/" + beanName()
		+ "\", method = RequestMethod.DELETE, produces = \"application/json; charset=utf-8\")");
	m.setReturnType(new FullyQualifiedJavaType("Long"));
	m.addParameter(
		new Parameter(new FullyQualifiedJavaType("Map<String, Object>"),
			"p",
			"@RequestBody"));
	m.addBodyLine("return obj.del(p);");
	return m;
}

private Method controller_delById() {
	// ---------------del-----------------------------
	// @RequestMapping(value = "/User/{id}", method = RequestMethod.DELETE, produces
	// = "application/json; charset=utf-8")
	// public Long delByid(@PathVariable Long id) {
	// return obj.delById(id);
	// }
	Method m = new Method();
	m.addJavaDocLine("// ---------del-------------------------");

	m.setVisibility(JavaVisibility.PUBLIC);
	m.setName("delById");
	m.addAnnotation("@RequestMapping(value = \"/" + beanName()
		+ "/{id}\", method = RequestMethod.DELETE, produces = \"application/json; charset=utf-8\")");
	m.setReturnType(new FullyQualifiedJavaType("Long"));
	m.addParameter(
		new Parameter(new FullyQualifiedJavaType("Long"),
			"id",
			"@PathVariable"));
	m.addBodyLine("return obj.delById(id);");

	return m;

}

private Method controller_insertBatch() {
	// @RequestMapping(value = "/User/batch", method = RequestMethod.PUT, produces
	// ="application/json; charset=utf-8")
	// public Long insertBatch(@RequestBody List<User> lst) {
	// return obj.insertBatch(lst);
	// }
	Method m = new Method();
	m.addJavaDocLine("// --------add-----update--------------------  ");
	m.addJavaDocLine("// put add or update,幂等操作，用于新增或修改数据  ");

	m.setVisibility(JavaVisibility.PUBLIC);
	m.setName("insertBatch");
	m.addAnnotation("@RequestMapping(value = \"/" + beanName()
		+ "/batch\", method = RequestMethod.PUT, produces =\"application/json; charset=utf-8\")");
	m.setReturnType(new FullyQualifiedJavaType("Long"));
	m.addParameter(
		new Parameter(new FullyQualifiedJavaType("List<" + beanName() + ">"),
			"lst",
			"@RequestBody"));
	m.addBodyLine("return obj.insertBatch(lst);");

	return m;
}

private Method controller_smartAdd() {
	// @RequestMapping(value = "/User", method = RequestMethod.PUT, produces =
	// "application/json; charset=utf-8")
	// public Long smartAdd(@RequestBody User entity) {
	// return obj.smartAdd(entity);
	// }

	Method m = new Method();
	m.addJavaDocLine(
		"// ------add or update-----------------  ");
	m.addJavaDocLine("// put add or update,幂等操作，用于新增或修改数据  ");

	m.setVisibility(JavaVisibility.PUBLIC);
	m.setName("smartAdd");
	m.addAnnotation("@RequestMapping(value = \"/" + beanName()
		+ "\", method = RequestMethod.PUT, produces = \"application/json; charset=utf-8\")");
	m.setReturnType(new FullyQualifiedJavaType("Long"));
	m.addParameter(
		new Parameter(new FullyQualifiedJavaType(beanName()),
			"entity",
			"@RequestBody"));
	m.addBodyLine("return obj.smartAdd(entity);");

	return m;
}

private Method controller_listPage() {
	// @RequestMapping(value = "/User/listPage/{pageNo}/{pageSize}", method =
	// RequestMethod.POST, produces = "application/json; charset=utf-8")
	// public PageBean<User> listPage(
	// @PathVariable("pageNo") int pageNo,
	// @PathVariable("pageSize") int pageSize,
	// @RequestBody Map<String, Object> p) {
	// return obj.listPage(pageNo, pageSize, p);
	// }

	Method m = new Method();
	m.addJavaDocLine("// ----------------query----------------  ");
	m.addJavaDocLine("// listPage 分页查询集合，复杂条件  ");

	m.setVisibility(JavaVisibility.PUBLIC);
	m.setName("listPage");
	m.addAnnotation("@RequestMapping(value = \"/" + beanName()
		+ "/listPage/{pageNo}/{pageSize}\", method = RequestMethod.POST, produces = \"application/json; charset=utf-8\")");
	m.setReturnType(new FullyQualifiedJavaType("PageBean<" + beanName() + ">"));
	// @PathVariable("pageNo") int pageNo
	m.addParameter(0,
		new Parameter(new FullyQualifiedJavaType("int"),
			"pageNo",
			"@PathVariable(\"pageNo\")"));
	// @PathVariable("pageSize") int pageSize,
	m.addParameter(1,
		new Parameter(new FullyQualifiedJavaType("int"),
			"pageSize",
			"@PathVariable(\"pageSize\")"));
	// @RequestBody Map<String, Object> p
	m.addParameter(2,
		new Parameter(new FullyQualifiedJavaType("Map<String, Object>"),
			"p",
			"@RequestBody"));

	m.addBodyLine("return obj.listPage(pageNo, pageSize, p);");
	return m;
}

private Method controller_list() {
	// @RequestMapping(value = "/User/list", method = RequestMethod.POST, produces =
	// "application/json; charset=utf-8")
	// public List<User> list(@RequestBody Map<String, Object> p) {
	// return obj.list(p);
	// }
	Method m = new Method();
	m.addJavaDocLine("// ----------------query----------------  ");
	m.addJavaDocLine("// list--post，查询集合，复杂条件  ");

	m.setVisibility(JavaVisibility.PUBLIC);
	m.setName("list");
	m.addAnnotation("@RequestMapping(value = \"/" + beanName()
		+ "/list\", method = RequestMethod.POST, produces = \"application/json; charset=utf-8\")");
	m.setReturnType(new FullyQualifiedJavaType("List<" + beanName() + ">"));
	m.addParameter(
		new Parameter(new FullyQualifiedJavaType("Map<String, Object>"),
			"p",
			"@RequestBody"));
	m.addBodyLine("return obj.list(p);");
	return m;
}

private Method controller_getMap() {
	// @RequestMapping(value = "/User/get", method = RequestMethod.POST, produces =
	// "application/json; charset=utf-8")
	// public User getMap(@RequestBody Map<String, Object> p) {
	// return obj.get(p);
	// }
	Method m = new Method();
	m.addJavaDocLine("// ----------------query----------------  ");
	m.addJavaDocLine("// get,复杂条件，查询单值	");

	m.setVisibility(JavaVisibility.PUBLIC);
	m.setName("getMap");
	m.addAnnotation(
		"@RequestMapping(value = \"/" + beanName()
			+ "/get\", method = RequestMethod.POST, produces =\"application/json; charset=utf-8\")");
	m.setReturnType(new FullyQualifiedJavaType(beanName()));
	m.addParameter(
		new Parameter(new FullyQualifiedJavaType("Map<String, Object>"), "p",
			"@RequestBody"));
	m.addBodyLine("return obj.get(p);");

	return m;
}

Method controller_get() {
	// @RequestMapping(value = "/User/{id}", method = RequestMethod.GET)
	// public User get(@PathVariable("id") Long id) {
	// return obj.getById(id);
	// }

	Method m = new Method();
	m.addJavaDocLine("// ----------------query----------------");
	m.addJavaDocLine("// get,通过主键查询单值");

	m.setName("get");
	m.addAnnotation(
		"@RequestMapping(value = \"/" + beanName() + "/{id}\", method = RequestMethod.GET)");
	m.setVisibility(JavaVisibility.PUBLIC);
	m.setReturnType(new FullyQualifiedJavaType(beanName()));
	// @PathVariable("id") Long id
	m.addParameter(
		new Parameter(new FullyQualifiedJavaType("Long"), "id", "@PathVariable(\"id\")"));
	m.addBodyLine("return obj.getById(id);");

	return m;
}

private TopLevelClass getServiceImpl() {
	CommentGenerator commentGenerator = context.getCommentGenerator();
	FullyQualifiedJavaType unitClass = new FullyQualifiedJavaType(
		serviceImplNameFull());

	TopLevelClass topClass = new TopLevelClass(unitClass);
	topClass.setVisibility(JavaVisibility.PUBLIC);

	//
	FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(serviceNameFull());

	topClass.addSuperInterface(superInterface);
	//
	topClass.setSuperClass("RootServiceImpl<" + beanName() + ">");

	commentGenerator.addJavaFileComment(topClass);

	// @Autowired
	// private UserMapper dao;
	Field fd = new Field();
	fd.setName("dao");
	fd.setType(new FullyQualifiedJavaType(mapperName()));
	fd.setVisibility(JavaVisibility.PRIVATE);
	fd.addAnnotation("@Autowired");
	topClass.addField(fd);
	//
	Method m = new Method();
	m.setVisibility(JavaVisibility.PROTECTED);
	m.setReturnType(new FullyQualifiedJavaType("RootMapper<" + beanName() + ">"));
	m.setName("getDao");
	m.addAnnotation("@Override");
	m.addBodyLine("return dao;");
	commentGenerator.addGeneralMethodComment(m, introspectedTable);
	topClass.addMethod(m);

	// annonation
	topClass.addAnnotation("@Service");
	//

	topClass.addImportedType("org.springframework.stereotype.Service");
	topClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
	topClass.addImportedType(beanNameFull());
	topClass.addImportedType(mapperNameFull());
	topClass.addImportedType(pPackName() + ".service.impl.RootServiceImpl");

	topClass.addImportedType(pPackName() + ".mapper.RootMapper");
	topClass.addImportedType(pPackName() + ".service.impl.RootServiceImpl");

	return topClass;
}

private Interface getService() {
	CommentGenerator commentGenerator = context.getCommentGenerator();
	FullyQualifiedJavaType unitClass = new FullyQualifiedJavaType(
		servicePackName() + "." + beanName() + "Service");

	Interface topInterface = new Interface(unitClass);

	// TopLevelClass topClass = new TopLevelClass(unitClass);
	topInterface.setVisibility(JavaVisibility.PUBLIC);
	//
	FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(
		"RootService<" + beanName() + ">");

	topInterface.addSuperInterface(superClass);

	commentGenerator.addJavaFileComment(topInterface);

	// topClass.setSuperClass("RootInterface<" + beanName + ">");
	// 引入要测试的mapper类

	// 测试内容
	// Method method = new Method();
	// method.setVisibility(JavaVisibility.PUBLIC);
	// method.setReturnType(new FullyQualifiedJavaType("void"));
	// method.setName("testAll");
	// method.addAnnotation("@Test");
	// method.addBodyLine("String a = \"hello world\";");

	// commentGenerator.addGeneralMethodComment(method, introspectedTable);
	// topInterface.addMethod(method);
	topInterface
		.addImportedType(new FullyQualifiedJavaType(beanPackName() + "." + beanName()));

	return topInterface;
}

private Interface getMapper() {
	CommentGenerator commentGenerator = context.getCommentGenerator();
	FullyQualifiedJavaType unitClass = new FullyQualifiedJavaType(
		mapperPackName() + "." + beanName() + "Mapper");

	Interface topInterface = new Interface(unitClass);

	// TopLevelClass topClass = new TopLevelClass(unitClass);
	topInterface.setVisibility(JavaVisibility.PUBLIC);
	//
	FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(
		"RootMapper<" + beanName() + ">");

	topInterface.addSuperInterface(superClass);
	commentGenerator.addJavaFileComment(topInterface);
	topInterface.addAnnotation("@Mapper");

	topInterface
		.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
	topInterface.addImportedType(new FullyQualifiedJavaType(beanNameFull()));
	return topInterface;
}

private String beanNameFull() {
	// com.wei.tempm.entity.Person;
	return pPackName() + "." + "entity." + beanName();
}

private String mapperNameFull() {
	return pPackName() + "." + "mapper." + beanName() + "Mapper";
}

private String serviceNameFull() {
	return pPackName() + "." + "service." + beanName() + "Service";
}

private String serviceImplNameFull() {
	return pPackName() + ".service.impl." + beanName() + "ServiceImpl";
}

}
