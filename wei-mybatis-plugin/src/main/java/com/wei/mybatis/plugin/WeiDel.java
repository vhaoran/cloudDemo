package com.wei.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

public class WeiDel {

public static XmlElement delById(Document doc, IntrospectedTable tb) {
	XmlElement node = new XmlElement("delete");
	node.addAttribute(new Attribute("id", "delById"));
	node.addAttribute(new Attribute("parameterType",
		"java.lang.Long"));
	// node.addAttribute(new Attribute("resultType",
	// tb.getBaseRecordType()));

	//
	StringBuilder sb = new StringBuilder();
	sb.append("delete from ");
	sb.append(tb.getFullyQualifiedTableNameAtRuntime());

	//
	String s = " where id=#{id} ";
	sb.append(s);

	node.addElement(new TextElement(sb.toString()));
	//
	return node;
}

public static XmlElement del(Document doc, IntrospectedTable tb) {
	XmlElement node = new XmlElement("delete");
	node.addAttribute(new Attribute("id", "del"));
	node.addAttribute(new Attribute("parameterType",
		"java.util.Map"));
	// node.addAttribute(new Attribute("resultType",
	// tb.getBaseRecordType()));

	//
	StringBuilder sb = new StringBuilder();
	sb.append("delete ");
	//
	sb.append(" from " + tb.getFullyQualifiedTableNameAtRuntime() + " ");
	node.addElement(new TextElement(sb.toString()));
	//
	XmlElement where = new XmlElement("where");
	XmlElement include = new XmlElement("include");
	include.addAttribute(new Attribute("refid", "sql_where"));
	//
	where.addElement(include);
	//
	node.addElement(where);
	//
	return node;
}

}
