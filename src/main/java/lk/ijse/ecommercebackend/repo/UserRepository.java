package lk.ijse.ecommercebackend.repo;

import lk.ijse.ecommercebackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
   Optional<User> findByEmail(String email);
   void deleteByEmail(String email);

}
