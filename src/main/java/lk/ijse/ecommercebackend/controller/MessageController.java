package lk.ijse.ecommercebackend.controller;

import lk.ijse.ecommercebackend.dto.MessageDTO;
import lk.ijse.ecommercebackend.email.EmailRequest;
import lk.ijse.ecommercebackend.entity.Message;
import lk.ijse.ecommercebackend.service.EmailService;
import lk.ijse.ecommercebackend.service.MessageService;
import lk.ijse.ecommercebackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/save/{userId}")
    public ResponseUtil sendMessage(@PathVariable int userId, @RequestBody String message) {
        messageService.saveMessage(userId, message);
        return new ResponseUtil(200, "Message sent successfully", null);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.ok(allMessages);
    }

    @GetMapping("/getAllForStaff")
    public List<MessageDTO> getAllMessagesForStaff() {
        return messageService.getAllMessagesForStaff();
    }

    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        boolean deleted = messageService.deleteMessageById(id);
        if (deleted) {
            return ResponseEntity.ok("Message deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found.");
        }
    }

    @PostMapping("/sendReplyEmail")
    public ResponseEntity<String> sendReply(@RequestBody EmailRequest request) {
        emailService.sendSimpleEmail(request.getTo(), request.getSubject(), request.getBody());
        return ResponseEntity.ok("Email sent successfully!");
    }

    @GetMapping("/getEmailByMessageId/{id}")
    public ResponseEntity<Map<String, String>> getEmailByMessageId(@PathVariable int id) {
        String email = messageService.getEmailByMessageId(id);
        Map<String, String> response = new HashMap<>();
        response.put("email", email);
        return ResponseEntity.ok(response);
    }
}