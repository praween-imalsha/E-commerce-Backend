package lk.ijse.ecommercebackend.service;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.ecommercebackend.dto.CategoryDTO;
import lk.ijse.ecommercebackend.entity.Category;
import lk.ijse.ecommercebackend.repo.CategoryRepo;
import lk.ijse.ecommercebackend.repo.ProductRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepo productRepo;

    public List<Category> getAllCategories() {
        return modelMapper.map(categoryRepo.findAll(),
                new TypeToken<List<Category>>() {}.getType());
    }

    public void saveCategory(CategoryDTO categoryDTO) {
        if (categoryRepo.existsById(categoryDTO.getId())) {
            throw new RuntimeException("Category already exists");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepo.save(category);
    }

    public Category getCategoryById(int id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category updateCategory(int id, Category categoryDto) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(categoryDto.getName());
        categoryRepo.save(category);

        return new Category(category.getId(), category.getName());
    }

    public void deleteCategory(int id) {
        if (!categoryRepo.existsById(id)) {
            throw new EntityNotFoundException("Category not found");
        }

        // Check if the category is associated with any products
        int productCount = productRepo.countByCategoryId(id);
        if (productCount > 0) {
            throw new RuntimeException("Cannot delete category. It is associated with " + productCount + " products.");
        }

        categoryRepo.deleteById(id);
    }
}
