package org.example.authservice.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Driver")
public class Driver extends BaseModel {

    private String name;

    @Column(nullable = false , unique = true)
    private String licenceNumber;

    // 1 : n , Driver : Booking
    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private final List<Booking> bookings = new ArrayList<>();

}