package cn.lcsz.adam.pbems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yufs
 */
@SpringBootApplication
@EnableTransactionManagement
public class PbemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PbemsApplication.class, args);
    }

}
