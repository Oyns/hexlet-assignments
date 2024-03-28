package exercise;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import exercise.component.UserProperties;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class Application {

    // Все пользователи
    private final List<User> users = Data.getUsers();
    private final UserProperties userProperties;

    // BEGIN
    @GetMapping("/admins")
    public List<String> getAdmins() {
        return users.stream()
                .filter(u -> userProperties.getAdmins().contains(u.getEmail()))
                .map(User::getName)
                .toList();
    }
    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        return users.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
