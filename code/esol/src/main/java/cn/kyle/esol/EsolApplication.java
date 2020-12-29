package cn.kyle.esol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Kyle
 */
@SpringBootApplication
@EnableTransactionManagement
public class EsolApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsolApplication.class, args);
    }

}
