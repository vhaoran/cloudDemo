package com.wei.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

public class WeiBaseColumn {

public static XmlElement exec(Document doc, IntrospectedTable tb) {
	XmlElement node = new XmlElement("sql");
	node.addAttribute(new Attribute("id", "Base_Column_tablename"));
	//
	StringBuilder sb = new StringBuilder();
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

	node.addElement(new TextElement(sb.toString()));
	return node;
}

}
