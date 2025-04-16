package lk.ijse.ecommercebackend.controller;

import lk.ijse.ecommercebackend.entity.Product;
import lk.ijse.ecommercebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(
            @ModelAttribute Product product,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam("categoryId") int categoryId
    ) throws IOException {
        Product savedProduct = productService.saveProductWithImage(product, imageFile, categoryId);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Product> updateProductImage(
            @PathVariable int id,
            @RequestParam("image") MultipartFile imageFile
    ) throws IOException {
        Product updatedProduct = productService.updateProductImage(id, imageFile);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getProductCount() {
        long count = productService.getProductCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int id) {
        Product product = productService.getProductById(id);

        if (product.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(determineImageType(product.getImageName())))
                .body(product.getImageData());
    }

    private String determineImageType(String filename) {
        if (filename == null) return "application/octet-stream";

        filename = filename.toLowerCase();
        if (filename.endsWith(".png")) return "image/png";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
        if (filename.endsWith(".gif")) return "image/gif";

        return "application/octet-stream";
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(
            @ModelAttribute Product product,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam("categoryId") int categoryId) {
        try {
            Product updatedProduct = productService.updateProduct(product, imageFile, categoryId);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable int categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    // 🔍 Search by title only
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("keyword") String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    // 🔍 Search + Filter by category
    @GetMapping("/search-by-category")
    public ResponseEntity<List<Product>> searchProductsByCategory(
            @RequestParam("keyword") String keyword,
            @RequestParam("categoryId") int categoryId
    ) {
        List<Product> products = productService.searchProductsByCategory(keyword, categoryId);
        return ResponseEntity.ok(products);
    }
}
