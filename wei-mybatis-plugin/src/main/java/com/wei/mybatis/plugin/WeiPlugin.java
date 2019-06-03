package com.wei.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class WeiPlugin extends PluginAdapter {

@Override
public boolean validate(List<String> warnings) {
	return true;
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

private String mapperNameFull(IntrospectedTable tb) {
	return pPackName() + "." + "mapper." + beanName(tb) + "Mapper";
}

private String beanName(IntrospectedTable tb) {
	FullyQualifiedTable table = tb.getFullyQualifiedTable();
	String beanName = table.getDomainObjectName();
	return beanName;
}

@Override
public boolean sqlMapDocumentGenerated(Document doc,
	IntrospectedTable tb) {

	debug("-----------------------------");
	debug("----enter sqlMapDocument---------");

	// set namespace
	XmlElement pNode = doc.getRootElement();
	pNode.addAttribute(new Attribute("namespace", mapperNameFull(tb)));
	//
	// sql_where
	XmlElement sql_where = WeiSql_where.exec(doc, tb);
	pNode.addElement(sql_where);

	// list--
	XmlElement list = WeiList.exec_list(doc, tb);
	pNode.addElement(list);

	// getById--
	XmlElement getById = WeiList.exec_getById(doc, tb);
	pNode.addElement(getById);

	// get--
	XmlElement get = WeiList.exec_get(doc, tb);
	pNode.addElement(get);
	//

	// baseColumn----add baseColumns,with full table name
	XmlElement baseColumn = WeiBaseColumn.exec(doc, tb);
	pNode.addElement(baseColumn);

	// insertBatch -----sql
	XmlElement insertBatch = WeiInsertBatch.exec(doc, tb);
	pNode.addElement(insertBatch);

	//
	XmlElement delById = WeiDel.delById(doc, tb);
	pNode.addElement(delById);

	//
	XmlElement del = WeiDel.del(doc, tb);
	pNode.addElement(del);

	//
	XmlElement update = WeiUpdate.update(doc, tb);
	pNode.addElement(update);

	return super.sqlMapDocumentGenerated(doc, tb);
}

void debug(Object o) {
	if (o != null) {
		System.out.println(o.toString());
	}
}

}
