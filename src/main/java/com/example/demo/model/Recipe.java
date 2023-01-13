package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name="portion_size")
    private String portionSize;
    private String pic;
    @Column(name="is_custom")
    private boolean isCustom;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Instruction[] instructions;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Iblock[] Iblocks;


}
