package com.example.config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月10日
 */
public class DynamicDataSource  extends AbstractRoutingDataSource {
	static Log log = LogFactory.getLog(DataSourceContextHolder.class);
    @Override
    protected Object determineCurrentLookupKey() {
        log.debug("数据源为{}"+DataSourceContextHolder.getDB());
        return DataSourceContextHolder.getDB();
    }
}
