package com.devpro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:Application.properties")
public class JPAConfig {
    private Environment environment;
public  JPAConfig(Environment environment)
{
    this.environment=environment;
}
@Bean
    public DataSource dataSource()
{
    DriverManagerDataSource dataSource=new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getProperty("className"));
    dataSource.setUsername(environment.getProperty("userName"));
    dataSource.setPassword(environment.getProperty("password"));
    dataSource.setUrl(environment.getProperty("url"));
    return  dataSource;
}
public Properties addtionalProperties()
{
     Properties properties =new Properties();
     properties.setProperty("hibernate.dialect",environment.getProperty("hibernate.dialect"));
     properties.setProperty("hibernate.show_sql",environment.getProperty("hibernate.show_sql"));
     properties.setProperty("hibernate.format_sql",environment.getProperty("hibernate.format_sql"));
     properties.setProperty("hibernate.enable_lazy_load_no_tran","true");
     properties.setProperty("hibernate.hbm2ddl.auto","create_drop");
     properties.setProperty("hibernate.hbm2dd.auto","update");
     return properties;
}
@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
{
    LocalContainerEntityManagerFactoryBean em=new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPersistenceUnitName("persistence-data");
    JpaVendorAdapter vendorAdapter =new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(addtionalProperties());
    return  em;
}
}
