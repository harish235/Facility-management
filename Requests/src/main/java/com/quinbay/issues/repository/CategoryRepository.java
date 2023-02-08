package com.quinbay.issues.repository;

import com.quinbay.issues.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findAll();
    Optional<Categories> findByCategoryName(String category);
}