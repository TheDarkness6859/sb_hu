package com.chat.sbhu.models;

import com.chat.sbhu.models.enums.City;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "venue")
@NoArgsConstructor
@AllArgsConstructor
public class Venue {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private City city;

    @Column(nullable = false, length = 40)
    private String address;

    @Column(nullable = false)
    private Integer capacity;

}
