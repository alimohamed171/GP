package com.GP.GP.security;

import com.GP.GP.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "privileges")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;// e.g. "ACCESS_ACCOMMODATION", "VIEW_DASHBOARD"

    @ManyToMany(mappedBy = "privileges")
    private List<User> users;

}
