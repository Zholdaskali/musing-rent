package kz.musin.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_user_addresses")
public class UserAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "auth_id")
    private UUID authId;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public UserAddresses(UUID authId, String street, String city, String country) {
        this.authId = authId;
        this.street = street;
        this.city = city;
        this.country = country;
    }
}
