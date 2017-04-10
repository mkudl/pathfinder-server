package pl.lodz.p.pathfinder.serv;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by QDL on 2017-01-07.
 */

@Configuration
@EnableTransactionManagement
//@ComponentScan({ "pl.lodz.p.pathfinder.serv" })
@PropertySource(value = { "classpath:application.properties"})
public class ProjectConfiguration
{
    @Autowired
    private Environment environment;

//    @Bean
//    DataSource dataSource()
//    {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
//        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
//        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
//        return dataSource;
//    }
//
//    private Properties hibernateProperties()
//        {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
//        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
//        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
//        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));//TODO update
//        return properties;
//    }

//    @Bean
//    public LocalSessionFactoryBean sessionFactory()
//    {
//        LocalSessionFactoryBean sessionFac = new LocalSessionFactoryBean();
//        sessionFac.setDataSource(dataSource());
//        sessionFac.setPackagesToScan(new String[] {"pl.lodz.p.pathfinder.serv"});
//        sessionFac.setHibernateProperties(hibernateProperties());
//        return sessionFac;
//    }

//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory sesFactory)
//    {
//        HibernateTransactionManager transManager = new HibernateTransactionManager();
//        transManager.setSessionFactory(sesFactory);
//        return transManager;
//    }


}
