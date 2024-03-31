package exercise.controller;

import exercise.exception.ResourceAlreadyExistsException;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {


    private final ProductRepository productRepository;

    @GetMapping(path = "")
    public List<Product> index() {
        return productRepository.findAll();
    }

    // BEGIN
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        val existingProduct = productRepository.findAll().stream()
                .filter(p -> p.equals(product))
                .toList();
        if (!existingProduct.isEmpty()) {
            throw new ResourceAlreadyExistsException("Продукт уже существует");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    @PutMapping(path = "/{id}")
    public Product update(@PathVariable long id, @RequestBody Product productData) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        product.setTitle(productData.getTitle());
        product.setPrice(productData.getPrice());

        productRepository.save(product);

        return product;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}
