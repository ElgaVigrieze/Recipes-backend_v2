package com.example.demo.repo;
import com.example.demo.model.Recipe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

//@Service("recipeService")
@Repository
public class RecipeCustomRepo {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RecipeRepo recipeRepo;
    @Transactional
    public long store(MultipartFile file, long id, HttpSession session) throws IOException {
        Recipe recipe = recipeRepo.findById(id).get();

        if(recipe.getId() > 0 ) {
                try {
                    String pic = "C:\\pics\\" + id+".jpg";
                    file.transferTo(new File(pic));
                    long result = updatePic(pic, id);
                    if (result > 0)
                        return result;
                    else
                        return -5;
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                    return -5;
                }
            }

        else {
            return 0;
        }
    }
    @Transactional
        public void setDefaultPic(Long id){
        var pic = "restaurant.png";
            updatePic(pic, id);
        }
    @Transactional
    public long updatePic(String pic, long id) {

        long result;

        try {
            var recipe = em.find(Recipe.class, id);
            recipe.setPic(pic);
            em.flush();
            result = recipe.getId();
            if(result > 0) {
                return result;
            }
            else return -5;
        }
        catch(Exception exception) {
            System.out.println("Error while updating pic: " + exception.getMessage());
            return -5;
        }
        finally {
            em.close();
        }
    }

    @Transactional
    public long saveIt(Recipe recipe) {
        try {
            em.persist(recipe);
            em.flush();
            return recipe.getId();
        }
        catch(Exception exception) {
            System.out.println("Exception in saving data into the database" + exception);
            return 0;
        }
    }

}
