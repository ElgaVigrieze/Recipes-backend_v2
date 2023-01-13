package com.example.demo.model;

import com.example.demo.enums.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Unit unit;
    private Float quantity;

//    public Ingredient(String name, Unit unit, Float quantity) {
//        this.name = name;
//        this.unit = unit;
//        this.quantity = quantity;
//    }
}
