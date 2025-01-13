package hua.gr.dit.Entitties;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String recipient;

    @Column
    private String message;

    @Column
    private String status;

    @Column
    private LocalDateTime sentAt;

    public Notification(Integer id, String recipient, String message, String status, LocalDateTime sentAt) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.status = status;
        this.sentAt = sentAt;
    }

    public Notification() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
