package kz.musin.userservice.repository;

import kz.musin.userservice.model.entity.UserAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAddressesRepository extends JpaRepository<UserAddresses, UUID> {

}
