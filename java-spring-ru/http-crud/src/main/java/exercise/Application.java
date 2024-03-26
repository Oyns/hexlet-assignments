package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @PostMapping("/posts")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return posts;
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPostById(@PathVariable String id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findAny();
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable String id,
                           @RequestBody Post post) {
        posts.forEach(p -> {
            if (p.getId().equals(id)) {
                p.setBody(post.getBody());
                p.setTitle(post.getTitle());
            }
        });

        return post;
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable String id) {
        var existingPost = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElse(null);

        if (Objects.nonNull(existingPost)) {
            posts.remove(existingPost);
        }
    }
    // END
}
