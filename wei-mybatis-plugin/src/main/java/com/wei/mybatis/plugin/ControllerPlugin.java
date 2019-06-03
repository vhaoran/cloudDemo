package com.wei.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.PluginAdapter;

public class ControllerPlugin
	extends PluginAdapter {

@Override
public boolean validate(List<String> warnings) {
	return true;
}

@Override
public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
	List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
	GeneratedJavaFile ctl = getControllerFile();
	if (ctl != null) {
		files.add(ctl);
	}

	return files;
}

private GeneratedJavaFile getControllerFile() {
	return null;
}

}
