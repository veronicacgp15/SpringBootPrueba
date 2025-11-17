package com.vcgp.proyecto.infrastructure.repository;

import com.vcgp.proyecto.infrastructure.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.name = :name AND (:idToIgnore IS NULL OR c.id != :idToIgnore)")
    Optional<Client> findByNameAndIdNot(@Param("name") String name, @Param("idToIgnore") Long idToIgnore);
}
