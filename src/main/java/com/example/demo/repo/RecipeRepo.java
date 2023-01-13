package com.example.demo.repo;

import com.example.demo.model.Recipe;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;


    public interface RecipeRepo extends JpaRepository<Recipe, Long> {



}
