package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instruction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
//    private int step;
    private String body;
//    @Transient
//    private Instruction[] instructions;

//    public int getStep() {
//        return java.util.Arrays.asList(instructions).indexOf(this);
//    }

}
