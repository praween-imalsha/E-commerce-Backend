package lk.ijse.ecommercebackend.service;

import lk.ijse.ecommercebackend.dto.MessageDTO;
import lk.ijse.ecommercebackend.entity.Message;
import lk.ijse.ecommercebackend.repo.MessageRepository;
import lk.ijse.ecommercebackend.repo.UserRepository;
import lk.ijse.ecommercebackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveMessage(int userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Message message = Message.builder()
                .user(user)
                .content(content)
                .sentAt(LocalDateTime.now())
                .build();
        messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll(); // Fetch all messages from the database
    }

    public List<MessageDTO> getAllMessagesForStaff() {
        List<Message> allMessages = messageRepository.findAll();
        return allMessages.stream()
                .map(MessageDTO::new) // Convert each Message to MessageDTO
                .toList(); // Collect the results into a List
    }

    public boolean deleteMessageById(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public String getEmailByMessageId(int id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
        return message.getUser().getEmail(); // Assuming your User entity has an `email` field
    }
}