package com.kakaopay.cafeservice.config.datasource;

import com.kakaopay.cafeservice.aop.SetDataSource.DataSourceType;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Profile("live")
public class RoutingDataSourceConfig {

    public static final String READER_DATASOURCE = "masterDataSource";
    public static final String WRITER_DATASOURCE = "slaveDataSource";

    @Bean(READER_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.hikari.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean(WRITER_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.hikari.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean
    @Primary
    @DependsOn({READER_DATASOURCE, WRITER_DATASOURCE})
    public DataSource routingDataSource(@Qualifier(READER_DATASOURCE) DataSource masterDataSource,
        @Qualifier(WRITER_DATASOURCE) DataSource slaveDataSource) {
        AbstractRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put(DataSourceType.READER, masterDataSource);
        targetDataSources.put(DataSourceType.WRITER, slaveDataSource);

        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.afterPropertiesSet();

        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

}
