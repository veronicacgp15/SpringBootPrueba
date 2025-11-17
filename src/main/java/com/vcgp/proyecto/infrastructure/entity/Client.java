package com.vcgp.proyecto.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Data
@ToString
@Builder
@NoArgsConstructor
public class Client extends IdentifiableEntity {



}

