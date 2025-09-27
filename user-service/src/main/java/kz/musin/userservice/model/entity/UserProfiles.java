package kz.musin.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_user_profiles")
public class UserProfiles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "auth_id")
    private UUID authId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UserProfiles(UUID authId, String fullName, String avatarUrl) {
        this.authId = authId;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.updatedAt = LocalDateTime.now();
    }

}
