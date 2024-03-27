package exercise;

import exercise.model.Post;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("posts")
public class Application {
    // Хранилище добавленных постов
    private final List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        val post = fetchPostById(id).orElse(null);

        if (Objects.nonNull(post)) {
            return ResponseEntity.ok().body(post);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable String id,
                                       @RequestBody Post post) {
        if (posts.stream().filter(p -> p.getId().equals(id)).toList().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        posts.forEach(p -> {
            if (p.getId().equals(id)) {
                p.setTitle(post.getTitle());
                p.setBody(post.getBody());
            }
        });
        return ResponseEntity.ok().body(post);
    }

    private Optional<Post> fetchPostById(String id) {
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .findAny();
    }
    // END

    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
