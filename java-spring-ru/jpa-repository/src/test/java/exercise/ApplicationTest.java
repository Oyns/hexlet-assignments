package exercise;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Product;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Test
    void testWelcomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void testShow() throws Exception {
        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void testIndex() throws Exception {

        var result = mockMvc.perform(get("/products")
                        .param("min", "50")
                        .param("max", "80"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        List<Product> products = om.readValue(body, new TypeReference<>() {
        });
        List<Integer> prices = products.stream().map(Product::getPrice).toList();

        assertThat(prices).allMatch(p -> p >= 50 && p <= 80).isSorted();
    }

    @Test
    void testIndexWithMinParam() throws Exception {

        var result = mockMvc.perform(get("/products")
                        .param("min", "50"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        List<Product> products = om.readValue(body, new TypeReference<>() {
        });
        List<Integer> prices = products.stream().map(Product::getPrice).toList();

        assertThat(prices).allMatch(p -> p >= 50).isSorted();
    }

    @Test
    void testIndexWithMaxParam() throws Exception {

        var result = mockMvc.perform(get("/products")
                        .param("max", "40"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        List<Product> products = om.readValue(body, new TypeReference<>() {
        });
        List<Integer> prices = products.stream().map(Product::getPrice).toList();

        assertThat(prices).allMatch(p -> p <= 40).isSorted();
    }

    @Test
    void testIndexWithoutParams() throws Exception {
        var result = mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        List<Product> products = om.readValue(body, new TypeReference<>() {
        });

        assertThat(products).hasSize(20);
    }
}
