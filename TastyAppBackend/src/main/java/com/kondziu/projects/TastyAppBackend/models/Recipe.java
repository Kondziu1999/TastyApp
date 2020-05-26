package com.kondziu.projects.TastyAppBackend.models;


import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "recipe")
public class Recipe {

    public Recipe(String name, String level,String time,String portions,List<String> ingredients, List<String> steps,String description, User user){
        this.name = name;
        this.level = level;
        this.time = time;
        this.portions = portions;
        this.ingredients = ingredients;
        this.steps = steps;
        this.description= description;
        this.user = user;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private String level;

    @Column(name="time")
    private String time;

    @Column(name = "portions")
    private String portions;

    @Column(name = "ingredients")
    @ElementCollection
    private List<String> ingredients=new ArrayList<>();

    @Column(name="steps")
    @ElementCollection
    private List<String> steps=new ArrayList<>();

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
