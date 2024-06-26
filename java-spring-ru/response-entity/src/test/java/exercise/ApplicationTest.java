package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Post;
import exercise.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() throws Exception {
        Post testPost = new Post("test-post", "Test post", "Test body");
        TestUtil.createTestPost(mockMvc, testPost);
    }

    @Test
    void testIndex() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", "32"));
    }

    @Test
    void testCreatePost() throws Exception {
        var post = new Post("another-post", "another post", "body");

        var request = post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(post));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(om.writeValueAsString(post)));
    }

    @Test
    void testShow() throws Exception {
        mockMvc.perform(get("/posts/test-post"))
                .andExpect(status().isOk());
    }

    @Test
    void testShowUnknown() throws Exception {
        mockMvc.perform(get("/posts/unknown-post"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePost() throws Exception {
        var post = new Post("test-post", "new title", "new body");

        var request = put("/posts/test-post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(post));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(post)));

        mockMvc.perform(get("/posts/test-post"))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(post)));
    }

    @Test
    void testUpdateUnknownPost() throws Exception {
        var post = new Post("unknown-post", "new title", "new body");

        var request = put("/posts/unknown-post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(post));

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/posts/test-post"))
                .andExpect(status().isOk());

    }
}
