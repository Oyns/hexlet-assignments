package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private Post testPost1;
    private Post testPost2;
    private Comment testComment1;
    private Comment testComment2;

    @BeforeEach
    public void setUp() {
        testPost1 = new Post();
        testPost1.setTitle("post1 title");
        testPost1.setBody("post1 body");
        postRepository.save(testPost1);

        testComment1 = new Comment();
        testComment1.setPostId(testPost1.getId());
        testComment1.setBody("comment1 body");
        commentRepository.save(testComment1);

        testPost2 = new Post();
        testPost2.setTitle("post2 title");
        testPost2.setBody("post2 body");
        postRepository.save(testPost2);

        testComment2 = new Comment();
        testComment2.setPostId(testPost2.getId());
        testComment2.setBody("comment2 body");
        commentRepository.save(testComment2);
    }

    @Test
    void testIndex() throws Exception {

        var result = mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    void testShow() throws Exception {
        mockMvc.perform(get("/comments/{id}", testComment1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(testComment1)));
    }

    @Test
    void testShowNegative() throws Exception {
        mockMvc.perform(get("/comments/{id}", 100))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testCreate() throws Exception {
        var comment = new Comment();
        comment.setBody("comment body ");

        var request = post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(comment));

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        var comment = new Comment();
        comment.setBody("body");

        var request = put("/comments/{id}", testComment1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(comment));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var actualComment = commentRepository.findById(testComment1.getId()).get();

        assertThat(actualComment.getBody()).isEqualTo(comment.getBody());
    }

    @Test
    void testUpdateNegative() throws Exception {
        var comment = new Comment();
        comment.setBody("body");

        var request = put("/products/{id}", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(comment));

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws Exception {

        mockMvc.perform(delete("/comments/{id}", testComment1.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/posts/{id}", testPost2.getId()))
                .andExpect(status().isOk());

        assertThat(commentRepository.findAll()).isEmpty();
    }
}
