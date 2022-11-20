package com.kakaopay.cafeservice.config.datasource;

import static com.kakaopay.cafeservice.aop.SetDataSource.DataSourceType;

import org.springframework.context.annotation.Profile;

@Profile("live")
public class RoutingDataSourceManager {

    private static final ThreadLocal<DataSourceType> currentDataSourceName = new ThreadLocal<>();

    public static DataSourceType getCurrentDataSourceName() {
        return currentDataSourceName.get();
    }

    public static void setCurrentDataSourceName(DataSourceType dataSourceType) {
        currentDataSourceName.set(dataSourceType);
    }

    public static void removeCurrentDataSourceName() {
        currentDataSourceName.remove();
    }
}