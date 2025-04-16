package lk.ijse.ecommercebackend.controller;

import lk.ijse.ecommercebackend.dto.CategoryDTO;
import lk.ijse.ecommercebackend.entity.Category;
import lk.ijse.ecommercebackend.service.CategoryService;
import lk.ijse.ecommercebackend.util.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("getAll")
    public ResponseUtil getAllCategories() {
        return new ResponseUtil(
                200,
                "Category List",
                categoryService.getAllCategories()
        );
    }

    @PostMapping("save")
    public ResponseUtil saveCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);
        return new ResponseUtil(
                200,
                "Category Saved",
                null
        );
    }

    @GetMapping("get/{id}")
    public ResponseUtil getCategoryById(@PathVariable("id") int id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseUtil(
                200,
                "Category Found",
                modelMapper.map(category, CategoryDTO.class)
        );
    }

    @PutMapping("/{id}")
    public ResponseUtil updateCategory(@PathVariable("id") int id, @RequestBody Category categoryDto) {
        return new ResponseUtil(
                200,
                "Category Updated",
                categoryService.updateCategory(id, categoryDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(Map.of("message", "Category deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
