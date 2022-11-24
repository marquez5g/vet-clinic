package com.example.vet.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Clinic {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    private String name;
    private Integer cost;
    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY)
    private Set<Pet> pets = new HashSet<>();

    @Override
    public String toString() {
        return "Clinic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
