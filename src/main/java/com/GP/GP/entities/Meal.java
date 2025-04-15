package com.GP.GP.entities;

import com.GP.GP.utill.Enums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "meals")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "meal_date", nullable = false)
    private LocalDate mealDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Enums.MealType mealType;

    @Column(length = 255)
    private String description;

    @Column(length = 255)
    private String image;

    @ManyToMany(mappedBy = "meals")
    private List<User> users;
}
