package lk.ijse.ecommercebackend.repo;

import lk.ijse.ecommercebackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(int categoryId);

    int countByCategoryId(int categoryId);

    // 🔍 Search by keyword only
    List<Product> findByTitleContainingIgnoreCase(String keyword);

    // 🔍 Search by keyword and category
    List<Product> findByTitleContainingIgnoreCaseAndCategoryId(String keyword, int categoryId);
}
