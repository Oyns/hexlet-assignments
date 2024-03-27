package exercise.controller.users;

import exercise.Data;
import exercise.model.Post;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BEGIN
@RestController
@RequestMapping("api/users")
public class PostsController {

    private final List<Post> posts = Data.getPosts();

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> getAllUserPosts(@PathVariable Integer id) {
        val userPosts = posts.stream()
                .filter(p -> p.getUserId() == id)
                .toList();

        return ResponseEntity.ok().body(userPosts);
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Post> saveNewPostForUser(@PathVariable Integer id,
                                                   @RequestBody Post post) {
        val newPost = new Post(
                id, post.getSlug(), post.getTitle(), post.getBody()
        );
        posts.add(newPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }
}
// END
