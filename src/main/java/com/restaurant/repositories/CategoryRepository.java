package com.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

}
