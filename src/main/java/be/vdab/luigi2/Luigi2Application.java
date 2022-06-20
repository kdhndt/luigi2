package be.vdab.luigi2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Luigi2Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Luigi2Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return super.configure(application);
        return application.sources(Luigi2Application.class);
    }
}
