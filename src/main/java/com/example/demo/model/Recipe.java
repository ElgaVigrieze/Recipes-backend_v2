package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
}
