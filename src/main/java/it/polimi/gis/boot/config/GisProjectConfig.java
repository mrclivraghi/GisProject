
package it.polimi.gis.boot.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({
    "it.anggen.*"
})
public class GisProjectConfig {

    @Value("${hibernate.format_sql}")
    private String formatSql;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.cache.region.factory_class}")
    private String cacheRegion;
    @Value("${datasource.driver.class.name}")
    private String driverClassName;
    @Value("${datasource.jdbc}")
    private String jdbcString;
    @Value("${datasource.url}")
    private String dbUrl;
    @Value("${datasource.port}")
    private String dbPort;
    @Value("${datasource.db.name}")
    private String dbName;
    @Value("${datasource.username}")
    private String dbUser;
    @Value("${datasource.password}")
    private String dbPassword;

    @Bean
    public SessionFactory sessionFactory() {
        org.springframework.orm.hibernate4.LocalSessionFactoryBuilder builder = 
        new org.springframework.orm.hibernate4.LocalSessionFactoryBuilder(dataSource());
         builder.scanPackages("it.anggen")
        .addProperties(getHibernateProperties());
        return builder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        java.util.Properties  prop = new java.util.Properties();
         prop.put("hibernate.format_sql", formatSql);
         prop.put("hibernate.show_sql", showSql);
         prop.put("hibernate.cache.region.factory_class", cacheRegion);
         return prop;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        org.apache.commons.dbcp.BasicDataSource ds = new org.apache.commons.dbcp.BasicDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(jdbcString+"://"+dbUrl+":"+dbPort+"/"+dbName);
        ds.setUsername(dbUser);
        ds.setPassword(dbPassword);
        return ds;
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        org.springframework.web.servlet.view.InternalResourceViewResolver viewResolver= new org.springframework.web.servlet.view.InternalResourceViewResolver();
        viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}
