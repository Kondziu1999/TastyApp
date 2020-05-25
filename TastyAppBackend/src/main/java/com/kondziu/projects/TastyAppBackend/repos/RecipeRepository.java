package com.kondziu.projects.TastyAppBackend.repos;

import com.kondziu.projects.TastyAppBackend.dto.XDD;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Integer> {

    Optional<Recipe> findById(Integer id);

    Optional<List<Recipe>> findAllBy();

    Page<Recipe> findAll(Pageable pageable);

    //query to return a part of field
        @Query(value="SELECT r.id, r.user_id , r.name , r.level , r.time,r.portions,r.description  from recipe r " , nativeQuery = true)
    List<XDD> findRecipeDbResult(Pageable page);



}
