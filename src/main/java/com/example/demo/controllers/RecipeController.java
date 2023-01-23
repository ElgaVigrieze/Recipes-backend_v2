
package com.example.demo.controllers;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import com.example.demo.repo.*;
import com.example.demo.repo.RecipeRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeController {

    @Autowired
    private RecipeRepo recipeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private IngredientRepo ingredientRepo;

    @RequestMapping("/login")
    public Long login(@RequestBody User user) {
        var userFound = userRepo.findByName(user.getName());
        if(userFound != null){
            if(userFound.getName().equals(user.getName()) && userFound.getPassword().equals(user.getPassword())){
                return userFound.getId();
            }
        }
        return 0L;
    }

    @RequestMapping("/user/{id}")
    public Object findUserById(@PathVariable Long id) {
        return userRepo.findById(id);
    }

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes(){
        return recipeRepo.findAll();
    }

    @PostMapping("/recipes")
    public long handleFileUpload(@RequestBody Recipe recipe) {
        return recipeRepo.saveIt(recipe);
    }

    @PostMapping("/recipes/uploadImage/{id}")
    public long handleFileUpload(@PathVariable int id ,
                                 @RequestParam("file") MultipartFile file, HttpSession session)
            throws IOException {
        return recipeRepo.store(file, id, session);
    }

    @GetMapping("/recipes/{id}")
    public Recipe getRecipeById(@PathVariable Long id){
        Recipe recipe = recipeRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Recipe doesn't exist"));
        return recipe;
    }

    @GetMapping(value="/recipes/imageDisplay/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable long id) throws IOException {
        var recipe = recipeRepo.getReferenceById(id);
        if(recipe.getPic() == null){
            return null;
        }
        Path imagePath = Path.of(recipe.getPic());
        return Files.readAllBytes(imagePath);
    }

    @GetMapping("/recipes/default/{id}")
    public boolean setDefaultPic(@PathVariable long id) {
        recipeRepo.setDefaultPic(id);
        return true;
    }

    @GetMapping("/recipes/last")
    public Recipe getLast(){
        var recipes = recipeRepo.findAll();
        return recipes.get(recipes.size()-1);
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipeUpdate){
        Recipe recipe = recipeRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Recipe doesn't exist"));
        recipe.setTitle(recipeUpdate.getTitle());
        recipe.setPortionSize(recipeUpdate.getPortionSize());
        recipe.setIblocks(recipeUpdate.getIblocks());
        recipe.setPic(recipeUpdate.getPic());
        recipe.setInstructions(recipeUpdate.getInstructions());
        recipe.setUser(recipeUpdate.getUser());
        Recipe updatedRecipe = recipeRepo.save(recipe);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteRecipe(@PathVariable Long id){
        recipeRepo.deleteById(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
