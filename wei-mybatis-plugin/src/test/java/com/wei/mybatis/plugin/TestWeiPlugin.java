
package com.wei.mybatis.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class TestWeiPlugin {

private File configFile;

@Before
public void before() {
	// 读取mybatis参数
	configFile = new File(
		"/home/whr/work_cloud/cloudDemo/wei-mybatis-plugin/src/test/java/com/wei/mybatis/plugin/generatorConfig.xml");
}

@Test
public void test() throws Exception {
	List<String> warnings = new ArrayList<String>();
	boolean overwrite = true;
	//
	ConfigurationParser parser = new ConfigurationParser(warnings);
	Configuration config = parser.parseConfiguration(configFile);
	DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	//
	MyBatisGenerator my = new MyBatisGenerator(config, callback, warnings);
	my.generate(null);
}
}
