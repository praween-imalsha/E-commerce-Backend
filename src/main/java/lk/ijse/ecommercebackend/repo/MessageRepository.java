package lk.ijse.ecommercebackend.repo;

import lk.ijse.ecommercebackend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByUserId(Integer userId);
}
