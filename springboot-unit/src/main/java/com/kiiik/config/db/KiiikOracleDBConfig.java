package com.kiiik.config.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = {
	"com.kiiik.web.khfl.readClientCtpData.mapper"
	/*"com.kiiik.pub.mybatis.dao",*/
	/*"com.kiiik.pub.mybatis.mapper",
	"com.kiiik.web.system.mapper"*/
	},
sqlSessionTemplateRef="qhKiiikSqlSessionTemplate")
public class KiiikOracleDBConfig {

	@Autowired
	Environment env;
	
	@Bean(name = "qhKiiikSqlSessionFactory")
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("qhKiiikDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver()
				.getResources(env.getProperty("mapper.oracle.xml.path")));
		return bean.getObject();
	}

	@Bean(name = "qhKiiikTransactionManager")
	public DataSourceTransactionManager testTransactionManager(@Qualifier("qhKiiikDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name = "qhKiiikSqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("qhKiiikSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
