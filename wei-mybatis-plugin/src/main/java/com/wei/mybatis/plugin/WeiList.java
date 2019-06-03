package com.wei.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

public class WeiList {

public static XmlElement exec_list(Document doc, IntrospectedTable tb) {
	XmlElement node = new XmlElement("select");
	node.addAttribute(new Attribute("id", "list"));
	node.addAttribute(new Attribute("parameterType",
		"java.util.Map"));
	node.addAttribute(new Attribute("resultType",
		tb.getBaseRecordType()));

	//
	StringBuilder sb = new StringBuilder();
	sb.append("select ");
	for (IntrospectedColumn col : ListUtilities
		.removeGeneratedAlwaysColumns(tb.getNonBLOBColumns())) {
		sb.append(tb.getFullyQualifiedTableNameAtRuntime());
		sb.append(".");
		sb.append(col.getActualColumnName());
		sb.append(",");
	}
	// 去掉最后的逗号
	if (sb.length() > 0) {
		sb.deleteCharAt(sb.length() - 1);
	}
	//
	sb.append(" from " + tb.getFullyQualifiedTableNameAtRuntime() + " ");
	node.addElement(new TextElement(sb.toString()));
	//
	XmlElement where = new XmlElement("where");
	XmlElement include = new XmlElement("include");
	include.addAttribute(new Attribute("refid", "sql_where"));
	where.addElement(include);
	//
	node.addElement(where);
	//
	String limit = "     <if test=\"iLimit != null\">\n" +
		"                        limit #{iLimit,jdbcType=INTEGER}\n" +
		"                </if>\n" +
		"";
	node.addElement(new TextElement(limit));
	//
	return node;
}

public static XmlElement exec_getById(Document doc, IntrospectedTable tb) {
	XmlElement node = new XmlElement("select");
	node.addAttribute(new Attribute("id", "getById"));
	node.addAttribute(new Attribute("parameterType",
		"java.lang.Long"));
	node.addAttribute(new Attribute("resultType",
		tb.getBaseRecordType()));

	//
	StringBuilder sb = new StringBuilder();
	sb.append("select ");
	for (IntrospectedColumn col : ListUtilities
		.removeGeneratedAlwaysColumns(tb.getNonBLOBColumns())) {
		sb.append(tb.getFullyQualifiedTableNameAtRuntime());
		sb.append(".");
		sb.append(col.getActualColumnName());
		sb.append(",");
	}
	// 去掉最后的逗号
	if (sb.length() > 0) {
		sb.deleteCharAt(sb.length() - 1);
	}
	//
	sb.append(" from " + tb.getFullyQualifiedTableNameAtRuntime() + " ");
	String s = " where id=#{id} ";
	sb.append(s);

	node.addElement(new TextElement(sb.toString()));
	//
	//
	// limit
	String limit = " limit 1 ";
	node.addElement(new TextElement(limit));

	//
	return node;
}

public static XmlElement exec_get(Document doc, IntrospectedTable tb) {
	XmlElement node = new XmlElement("select");
	node.addAttribute(new Attribute("id", "get"));
	node.addAttribute(new Attribute("parameterType",
		"java.util.Map"));
	node.addAttribute(new Attribute("resultType",
		tb.getBaseRecordType()));

	//
	StringBuilder sb = new StringBuilder();
	sb.append("select ");
	for (IntrospectedColumn col : ListUtilities
		.removeGeneratedAlwaysColumns(tb.getNonBLOBColumns())) {
		sb.append(tb.getFullyQualifiedTableNameAtRuntime());
		sb.append(".");
		sb.append(col.getActualColumnName());
		sb.append(",");
	}
	// 去掉最后的逗号
	if (sb.length() > 0) {
		sb.deleteCharAt(sb.length() - 1);
	}
	//
	sb.append(" from " + tb.getFullyQualifiedTableNameAtRuntime() + " ");
	node.addElement(new TextElement(sb.toString()));
	//
	XmlElement where = new XmlElement("where");
	XmlElement include = new XmlElement("include");
	include.addAttribute(new Attribute("refid", "sql_where"));

	where.addElement(include);
	//
	node.addElement(where);
	// limit
	String limit = " limit 1 ";
	node.addElement(new TextElement(limit));

	//
	return node;
}

}
