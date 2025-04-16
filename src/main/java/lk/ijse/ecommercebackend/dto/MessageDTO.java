package lk.ijse.ecommercebackend.dto;

import lk.ijse.ecommercebackend.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private int id;
    private String name ;// ID of the user sending the message
    private String content; // Message text
    private LocalDateTime sentAt; // Timestamp of message sent

    // Constructor
    public MessageDTO(Message message) {
        this.id = message.getId();
        this.name = message.getUser().getFirstname();
        this.content = message.getContent().replaceAll("\\\\", "");  // Remove extra escape characters
        this.sentAt = message.getSentAt();
    }
}
