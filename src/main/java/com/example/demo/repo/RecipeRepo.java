package com.example.demo.repo;

import com.example.demo.model.Recipe;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    public long store(MultipartFile file, long id, HttpSession session);

    public void setDefaultPic(Long id);

    public long updatePic(String pic, long id);

    public long saveIt(Recipe recipe);

    public List<Recipe> showAll();
}
