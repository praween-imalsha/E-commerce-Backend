package lk.ijse.ecommercebackend.service;

import lk.ijse.ecommercebackend.entity.Category;
import lk.ijse.ecommercebackend.entity.Product;
import lk.ijse.ecommercebackend.repo.CategoryRepo;
import lk.ijse.ecommercebackend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private CategoryRepo categoryRepository;

    public Product saveProductWithImage(Product product, MultipartFile imageFile, int categoryId) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageData(imageFile.getBytes());
            product.setImageName(imageFile.getOriginalFilename());
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product updateProductImage(int productId, MultipartFile imageFile) throws IOException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setImageData(imageFile.getBytes());
        existingProduct.setImageName(imageFile.getOriginalFilename());

        return productRepository.save(existingProduct);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(int id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public long getProductCount() {
        return productRepository.count();
    }

    @Transactional
    public Product updateProduct(Product product, MultipartFile imageFile, Integer categoryId) throws IOException {
        if (productRepository.existsById(product.getId())) {
            product.setTitle(product.getTitle());
            product.setPrice(product.getPrice());
            product.setQuantity(product.getQuantity());

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);

            if (imageFile != null && !imageFile.isEmpty()) {
                product.setImageData(imageFile.getBytes());
                product.setImageName(imageFile.getOriginalFilename());
            }

            return productRepository.save(product);
        } else {
            return null;
        }
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // 🔍 Search only
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // 🔍 Search with category filter
    public List<Product> searchProductsByCategory(String keyword, int categoryId) {
        return productRepository.findByTitleContainingIgnoreCaseAndCategoryId(keyword, categoryId);
    }
}
