package com.wei.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

public class WeiUpdate {

public static XmlElement update(Document doc, IntrospectedTable tb) {
	XmlElement node = new XmlElement("update");
	node.addAttribute(new Attribute("id", "update"));
	node.addAttribute(new Attribute("parameterType",
		tb.getBaseRecordType()));
	// node.addAttribute(new Attribute("resultType",
	// tb.getBaseRecordType()));

	//
	StringBuilder sb = new StringBuilder();
	sb.append("update " + tb.getFullyQualifiedTableNameAtRuntime() + " ");
	sb.append(" set ");
	for (IntrospectedColumn col : ListUtilities
		.removeGeneratedAlwaysColumns(tb.getNonBLOBColumns())) {
		// skip id column
		if (col.getActualColumnName().equals("id")) {
			continue;
		}

		sb.append(col.getActualColumnName());
		sb.append(" = #{" + col.getActualColumnName() + ",jdbcType=" +
			col.getJdbcTypeName() + "},");
	}
	// 去掉最后的逗号
	if (sb.length() > 0) {
		sb.deleteCharAt(sb.length() - 1);
	}
	//
	sb.append(" where id = #{id} ");

	//
	node.addElement(new TextElement(sb.toString()));
	//
	// XmlElement where = new XmlElement("where");
	// XmlElement include = new XmlElement("include");
	// include.addAttribute(new Attribute("refid", "sql_where"));
	// where.addElement(include);
	// node.addElement(where);
	//
	return node;
}

}
