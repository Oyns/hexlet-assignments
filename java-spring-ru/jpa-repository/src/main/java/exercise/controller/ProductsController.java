package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductRepository productRepository;

    // BEGIN
    @GetMapping
    public List<Product> getPagedProductsSortedByPrice(@RequestParam(defaultValue = "0", name = "min") Integer startsFrom,
                                                       @RequestParam(required = false, name = "max") Integer endsWith) {

        if (Objects.isNull(endsWith)) {
            endsWith = productRepository.findAll().stream()
                    .map(Product::getPrice)
                    .max(Integer::compareTo)
                    .orElseThrow(() -> new ResourceNotFoundException("Не фортануло"));
        }

        return productRepository.findAllByPriceBetweenOrderByPriceAsc(startsFrom, endsWith);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }
}
