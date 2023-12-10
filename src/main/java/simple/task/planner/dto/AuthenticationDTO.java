package simple.task.planner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import simple.task.planner.entities.User;
@Schema(description = "Сущность для аутентификации")
public class AuthenticationDTO {
    @NotBlank(message = "Email name must be pointed")
    @Email
    @Schema(description = "Email пользователя")
    private String email;
    @NotBlank(message = "Password must must not be empty")
    @Schema(description = "Пароль для аутентификации")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User getUser() {
        User user = new User();
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        return user;
    }
}
