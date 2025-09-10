package kz.musin.authservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_refresh_tokens")
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "auth_id")
    private UUID authId;

    @Column(name = "token")
    private String token;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "create_at")
    private LocalDateTime createAt;


    public RefreshToken(UUID authId, String token) {
        this.authId = authId;
        this.token = token;
        this.createAt = LocalDateTime.now();
        this.expiresAt = createAt.plusDays(7); // срок действия 7 дней
    }

    public RefreshToken() {

    }
}
