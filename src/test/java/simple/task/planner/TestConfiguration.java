package simple.task.planner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import simple.task.planner.security.JWTUtil;

@Configuration
public class TestConfiguration {
    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }
}
