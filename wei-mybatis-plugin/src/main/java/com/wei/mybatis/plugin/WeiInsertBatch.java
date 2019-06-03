package com.wei.mybatis.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class WeiInsertBatch {

public static XmlElement exec(Document doc, IntrospectedTable tb) {
	// XmlElement node = new XmlElement("insert");
	//
	XmlElement node = new XmlElement("insert"); //$NON-NLS-1$
	node.addAttribute(new Attribute("id", "insertBatch"));
	node.addAttribute(new Attribute("parameterType", "java.util.List"));

	StringBuilder str = new StringBuilder();
	str.append("insert into ");
	// $NON-NLS-1$
	str.append(tb.getFullyQualifiedTableNameAtRuntime());
	str.append(" (");
	// $NON-NLS-1$

	StringBuilder values = new StringBuilder();
	values.append("("); //$NON-NLS-1$

	XmlElement each = new XmlElement("foreach");
	each.addAttribute(new Attribute("collection", "list"));
	each.addAttribute(new Attribute("index", "index"));
	each.addAttribute(new Attribute("item", "item"));
	each.addAttribute(new Attribute("separator", ","));

	List<String> valuesClauses = new ArrayList<String>();
	List<IntrospectedColumn> cols = ListUtilities
		.removeIdentityAndGeneratedAlwaysColumns(tb.getNonPrimaryKeyColumns());
	for (int i = 0; i < cols.size(); i++) {
		IntrospectedColumn col = cols.get(i);

		str
			.append(MyBatis3FormattingUtilities.getEscapedColumnName(col));
		values.append(getParam(col));
		if (i + 1 < cols.size()) {
			str.append(", "); //$NON-NLS-1$
			values.append(", "); //$NON-NLS-1$
		}

		if (values.length() > 80) {
			node.addElement(new TextElement(str.toString()));
			str.setLength(0);
			OutputUtilities.xmlIndent(str, 1);

			valuesClauses.add(values.toString());
			values.setLength(0);
			OutputUtilities.xmlIndent(values, 1);
		}
	}

	str.append(')');
	node.addElement(new TextElement(str.toString()));

	node.addElement(new TextElement("values"));

	node.addElement(each);
	values.append(')');
	valuesClauses.add(values.toString());

	for (String clause : valuesClauses) {
		each.addElement(new TextElement(clause));
	}

	// update segment
	StringBuilder update = new StringBuilder("ON DUPLICATE KEY UPDATE\n");
	for (int i = 0; i < cols.size(); i++) {
		IntrospectedColumn c = cols.get(i);
		update.append(c.getActualColumnName());
		update.append(" = ");
		update.append("values(");
		update.append(c.getActualColumnName());
		update.append("),");
	}
	update.deleteCharAt(update.length() - 1);
	TextElement updateElement = new TextElement(update.toString());
	node.addElement(updateElement);

	//
	return node;
}

static String getParam(IntrospectedColumn col) {
	StringBuilder sb = new StringBuilder();

	sb.append("#{item."); //$NON-NLS-1$
	sb.append(col.getJavaProperty());
	sb.append(",jdbcType="); //$NON-NLS-1$
	sb.append(col.getJdbcTypeName());

	if (stringHasValue(col.getTypeHandler())) {
		sb.append(",typeHandler="); //$NON-NLS-1$
		sb.append(col.getTypeHandler());
	}

	sb.append('}');

	return sb.toString();
}

}
