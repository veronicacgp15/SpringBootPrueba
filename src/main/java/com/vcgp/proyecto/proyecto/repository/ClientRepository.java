package com.vcgp.proyecto.proyecto.repository;

import com.vcgp.proyecto.proyecto.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
