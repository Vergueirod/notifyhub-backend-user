package com.vergueiro_group.notifyhub_backend_user.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="telefones")
@Builder
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="number", length=10)
    private String number;

    @Column(name="ddd", length=3)
    private String ddd;

//    @Column(name="user_id")
//    private UUID user_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

}
