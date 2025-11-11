package com.vcgp.proyecto.proyecto.infrastructure.repository;

import com.vcgp.proyecto.proyecto.infrastructure.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
