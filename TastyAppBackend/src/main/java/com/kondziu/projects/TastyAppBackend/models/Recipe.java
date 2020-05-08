package com.kondziu.projects.TastyAppBackend.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
}
