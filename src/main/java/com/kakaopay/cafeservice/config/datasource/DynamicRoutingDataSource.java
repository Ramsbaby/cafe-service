package com.kakaopay.cafeservice.config.datasource;

import com.kakaopay.cafeservice.aop.SetDataSource.DataSourceType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Profile("live")
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        DataSourceType dataSourceType = RoutingDataSourceManager.getCurrentDataSourceName();
        if (dataSourceType == null) {
            return DataSourceType.READER;
        }

        RoutingDataSourceManager.removeCurrentDataSourceName();
        return dataSourceType;
    }

}