package com.vcgp.proyecto.proyecto.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends IdentifiableEntity {

}

