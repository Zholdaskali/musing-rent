package kz.musin.userservice.repository;

import kz.musin.userservice.model.entity.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfiles, UUID> {

    Optional<UserProfiles> findByAuthId(UUID authId);
}
