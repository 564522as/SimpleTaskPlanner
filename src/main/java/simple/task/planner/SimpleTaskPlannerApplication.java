package simple.task.planner;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import simple.task.planner.security.JWTUtil;

@SpringBootApplication
public class SimpleTaskPlannerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleTaskPlannerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
