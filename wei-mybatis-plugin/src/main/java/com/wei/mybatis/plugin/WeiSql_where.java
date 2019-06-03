package com.wei.mybatis.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class WeiSql_where {

public static XmlElement exec(Document doc, IntrospectedTable tb) {
	XmlElement node = new XmlElement("sql"); //$NON-NLS-1$
	node.addAttribute(new Attribute("id", "sql_where"));

	for (IntrospectedColumn introspectedColumn : ListUtilities
		.removeGeneratedAlwaysColumns(tb.getAllColumns())) {
		node.addElement(genEq(tb, introspectedColumn));

		if (!"TIMESTAMP".equals(introspectedColumn.getJdbcTypeName())) {
			node.addElement(genIn(tb, introspectedColumn, false));
			node.addElement(genIn(tb, introspectedColumn, true));
		}

		if (!"CHAR".equals(introspectedColumn.getJdbcTypeName())
			&& !"VARCHAR".equals(introspectedColumn.getJdbcTypeName())) {
			node.addElement(genStart(tb, introspectedColumn, false));
			node.addElement(genEnd(tb, introspectedColumn, false));
			node.addElement(genStart(tb, introspectedColumn, true));
			node.addElement(genEnd(tb, introspectedColumn, true));
		} else {
			node.addElement(genLike(tb, introspectedColumn));
		}
		node.addElement(genNotEq(tb, introspectedColumn));
		node.addElement(new TextElement(""));
		node.addElement(new TextElement(""));

	}
	XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
	isNotNullElement.addAttribute(new Attribute("test", "dynamicsql != null")); //$NON-NLS-1$
	TextElement dElement = new TextElement("AND ${dynamicsql}");
	isNotNullElement.addElement(dElement);
	node.addElement(isNotNullElement);
	//
	return node;
}

static XmlElement genLike(IntrospectedTable introspectedTable,
	IntrospectedColumn introspectedColumn) {
	StringBuilder sb = new StringBuilder();

	sb.setLength(0);
	sb.append(introspectedColumn.getJavaProperty());
	sb.append("_like");
	sb.append(" != null"); //$NON-NLS-1$
	XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
	isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

	sb.setLength(0);
	sb.append("AND ");
	sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
	sb.append(".");
	sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
	sb.append(" like "); //$NON-NLS-1$
	sb.append("concat(\"%\" , ");
	sb.append(getParameterClause(introspectedColumn, "_like"));
	sb.append(" ,\"%\")");

	isNotNullElement.addElement(new TextElement(sb.toString()));

	return isNotNullElement;
}

static XmlElement genEq(IntrospectedTable introspectedTable,
	IntrospectedColumn introspectedColumn) {
	StringBuilder sb = new StringBuilder();

	sb.setLength(0);
	sb.append(introspectedColumn.getJavaProperty());
	sb.append(" != null"); //$NON-NLS-1$
	XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
	isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

	sb.setLength(0);
	sb.append("AND ");
	sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
	sb.append(".");
	sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
	sb.append(" = "); //$NON-NLS-1$
	sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

	isNotNullElement.addElement(new TextElement(sb.toString()));

	return isNotNullElement;
}

static XmlElement genNotEq(IntrospectedTable introspectedTable,
	IntrospectedColumn introspectedColumn) {
	StringBuilder sb = new StringBuilder();

	sb.setLength(0);
	sb.append(introspectedColumn.getJavaProperty());
	sb.append("_not_eq");
	sb.append(" != null"); //$NON-NLS-1$
	XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
	isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

	sb.setLength(0);
	sb.append("AND ");
	sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
	sb.append(".");
	sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
	sb.append(" != "); //$NON-NLS-1$
	sb.append(getParameterClause(introspectedColumn, "_not_eq"));

	isNotNullElement.addElement(new TextElement(sb.toString()));

	return isNotNullElement;
}

static XmlElement genStart(IntrospectedTable introspectedTable,
	IntrospectedColumn introspectedColumn, boolean only) {
	StringBuilder sb = new StringBuilder();

	sb.setLength(0);
	sb.append(introspectedColumn.getJavaProperty());
	sb.append("_start");
	if (only) {
		sb.append("_only");
	}
	sb.append(" != null"); //$NON-NLS-1$
	XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
	isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

	sb.setLength(0);
	sb.append("AND ");
	sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
	sb.append(".");
	sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
	if (only) {
		sb.append(" <![CDATA[ > ]]> "); //$NON-NLS-1$
	} else {
		sb.append(" <![CDATA[ >= ]]> "); //$NON-NLS-1$
	}

	String end = "_start";
	if (only) {
		end = end + "_only";
	}
	sb.append(getParameterClause(introspectedColumn, end));

	isNotNullElement.addElement(new TextElement(sb.toString()));

	return isNotNullElement;
}

static XmlElement genEnd(IntrospectedTable introspectedTable,
	IntrospectedColumn introspectedColumn, boolean only) {
	StringBuilder sb = new StringBuilder();

	sb.setLength(0);
	sb.append(introspectedColumn.getJavaProperty());
	sb.append("_end");
	if (only) {
		sb.append("_only");
	}
	sb.append(" != null"); //$NON-NLS-1$
	XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
	isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

	sb.setLength(0);
	sb.append("AND ");
	sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
	sb.append(".");
	sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
	if (only) {
		sb.append(" <![CDATA[ < ]]> "); //$NON-NLS-1$
	} else {
		sb.append(" <![CDATA[ <= ]]> "); //$NON-NLS-1$
	}

	String end = "_end";
	if (only) {
		end = end + "_only";
	}

	sb.append(getParameterClause(introspectedColumn, end));

	isNotNullElement.addElement(new TextElement(sb.toString()));

	return isNotNullElement;
}

static XmlElement genIn(IntrospectedTable introspectedTable,
	IntrospectedColumn introspectedColumn, boolean notin) {
	String string = "";
	StringBuilder sb = new StringBuilder();

	sb.setLength(0);
	sb.append(introspectedColumn.getJavaProperty());
	if (notin) {
		sb.append("_not");
	}
	sb.append("_in");

	string = sb.toString();

	sb.append(" != null"); //$NON-NLS-1$
	XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
	isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

	sb.setLength(0);
	sb.append("AND ");
	sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
	sb.append(".");
	sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
	if (notin) {
		sb.append(" not ");
	}
	sb.append(" in "); //$NON-NLS-1$
	isNotNullElement.addElement(new TextElement(sb.toString()));
	XmlElement foreach = new XmlElement("foreach");
	foreach.addAttribute(new Attribute("item", "item"));
	foreach.addAttribute(new Attribute("index", "index"));
	foreach.addAttribute(new Attribute("collection", string));
	foreach.addAttribute(new Attribute("open", "("));
	foreach.addAttribute(new Attribute("separator", ","));
	foreach.addAttribute(new Attribute("close", ")"));
	sb.setLength(0);
	if ("CHAR".equals(introspectedColumn.getJdbcTypeName())
		|| "VARCHAR".equals(introspectedColumn.getJdbcTypeName())) {
		sb.append("'");
	}
	sb.append("${item}");
	if ("CHAR".equals(introspectedColumn.getJdbcTypeName())
		|| "VARCHAR".equals(introspectedColumn.getJdbcTypeName())) {
		sb.append("'");
	}
	TextElement textElement = new TextElement(sb.toString());
	foreach.addElement(textElement);
	isNotNullElement.addElement(foreach);

	return isNotNullElement;
}

static String getParameterClause(IntrospectedColumn introspectedColumn, String end) {
	StringBuilder sb = new StringBuilder();

	sb.append("#{"); //$NON-NLS-1$
	sb.append(introspectedColumn.getJavaProperty() + end);
	sb.append(",jdbcType="); //$NON-NLS-1$
	sb.append(introspectedColumn.getJdbcTypeName());

	if (stringHasValue(introspectedColumn.getTypeHandler())) {
		sb.append(",typeHandler="); //$NON-NLS-1$
		sb.append(introspectedColumn.getTypeHandler());
	}

	sb.append('}');

	return sb.toString();
}

}
