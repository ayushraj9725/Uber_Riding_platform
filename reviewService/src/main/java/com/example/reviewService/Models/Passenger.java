package com.example.reviewService.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Passenger")
public class Passenger extends BaseModel{

    private String name ;

    @OneToMany(mappedBy = "passenger" , cascade = CascadeType.ALL)
    private final List<Booking> bookings = new ArrayList<>();

}
