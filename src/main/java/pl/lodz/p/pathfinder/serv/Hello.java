package pl.lodz.p.pathfinder.serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by QDL on 2017-01-05.
 */



    @SpringBootApplication
public class Hello
{
    public static void main(String[] args)
    {
        SpringApplication.run(Hello.class,args);
    }
}
