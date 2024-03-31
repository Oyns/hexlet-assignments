package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductRepository productRepository;

    // BEGIN
    @GetMapping
    public List<Product> getPagedProductsSortedByPrice(@RequestParam(defaultValue = "0", name = "min") Integer startsFrom,
                                                       @RequestParam(defaultValue = "1000000", name = "max") Integer endsWith) {

        return productRepository.findAllByPriceBetweenOrderByPriceAsc(startsFrom, endsWith);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }
}
