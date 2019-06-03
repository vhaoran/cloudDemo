package com.wei.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.config.PropertyRegistry;

public class WeiClientPlugin extends PluginAdapter {

@Override
public boolean validate(List<String> warnings) {
	return true;
}

@Override
public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
	return super.contextGenerateAdditionalJavaFiles();
}

@Override
public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
	IntrospectedTable tb) {

	List<GeneratedJavaFile> answer = tb
		.getGeneratedJavaFiles();

	WeiClientGenerator gen = new WeiClientGenerator(this.properties);
	gen.setIntrospectedTable(tb);
	gen.setContext(this.context);

	List<CompilationUnit> compilationUnits = gen.getCompilationUnits();
	for (CompilationUnit compilationUnit : compilationUnits) {
		GeneratedJavaFile gjf = new GeneratedJavaFile(
			compilationUnit,
			context.getJavaModelGeneratorConfiguration()
				.getTargetProject(),
			context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
			context.getJavaFormatter());
		answer.add(gjf);
	}

	return answer;
}
}
