package pl.lodz.p.pathfinder.serv;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.AbstractAuditable_;
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
@PropertySource(value = { "classpath:application.properties"})
public class ProjectConfiguration
{
    @Bean
    HttpTransport httpTransport()
    {
        return new NetHttpTransport();
    }

    @Bean
    JsonFactory jsonFactory()
    {
        return  new JacksonFactory();
    }
}
