package com.webbdealer.detailing.vehicle.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends CrudRepository<Model, Long> {

    Optional<List<Model>> findAllByName(String name);
    
    Optional<Model> findByNameIgnoreCase(String name);

    Optional<Model> findFirstByNameIgnoreCase(String name);

    Optional<Model> findByNameContainingIgnoreCase(String name);

    Optional<Model> findByNameLike(String name);

    Optional<Model> findByNameStartingWith(String name);
    
    Optional<Model> findByNameAndMake(String modelName, Make make);

    @Query("select model.name from Model model join model.make make where make.name = :makeName")
    List<String> findAllModelsByMake(@Param("makeName") String makeName);

    @Query("select model from Model model join model.make make where make.name = :makeName and model.name like %:modelName%")
    Optional<Model> findModelByMakeLike(@Param("makeName") String makeName, @Param("modelName") String modelName);
    
    @Query("select model from Model model join model.make make where make.name = :makeName and model.name = :modelName")
    Optional<Model> findModelByMake(@Param("makeName") String makeName, @Param("modelName") String modelName);
}
