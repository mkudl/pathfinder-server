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



//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
    @SpringBootApplication
//@EntityScan(basePackages = {"pl.lodz.p.pathfinder.serv.model","pl.lodz.p.pathfinder.serv"})

public class Hello
{
    public static void main(String[] args)
    {
        SpringApplication.run(Hello.class,args);
    }
}
