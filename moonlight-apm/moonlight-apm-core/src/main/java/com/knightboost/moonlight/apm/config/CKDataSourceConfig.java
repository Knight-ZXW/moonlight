package com.knightboost.moonlight.apm.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/*
 * Created by Knight-ZXW on 2022/7/20
 * email: nimdanoob@163.com
 */
@Configuration
@MapperScan(basePackages = {"com.knightboost.moonlight.**.chdao"},
        sqlSessionFactoryRef = "clickHouseSqlSessionFactory")
public class CKDataSourceConfig {

    @Bean(name = "clickHouseDataSource")
    @ConfigurationProperties(prefix = "moonlight.clickhouse")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    //todo yml配置化
    @Bean("clickHouseSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("clickHouseDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/clickhouse/**.xml"));
        factoryBean.setTypeHandlersPackage("com.knightboost.moonlight.clickhouse.typehandlers");
        return factoryBean.getObject();
    }

    @Bean("clickHouseSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("clickHouseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
