package com.kakaopay.cafeservice.aop;

import com.kakaopay.cafeservice.config.datasource.RoutingDataSourceManager;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("live")
public class SetDataSourceAspect {

    @Before("@annotation(SetDataSource) && @annotation(target)")
    public void setDataSource(SetDataSource target) throws RuntimeException {

        if (target.dataSourceType() == SetDataSource.DataSourceType.READER ||
            target.dataSourceType() == SetDataSource.DataSourceType.WRITER) {
            RoutingDataSourceManager.setCurrentDataSourceName(target.dataSourceType());
        } else {
            throw new RuntimeException("Datasource is not valid");
        }
    }
}