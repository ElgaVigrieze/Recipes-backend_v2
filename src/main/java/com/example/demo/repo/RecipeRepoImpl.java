package com.example.demo.repo;
import com.example.demo.model.Recipe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class RecipeRepoImpl {

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private RecipeRepo recipeRepo;

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

        public void setDefaultPic(Long id){
        var pic = "restaurant.png";
            updatePic(pic, id);
        }

    public long updatePic(String pic, long id) {

        long result;

        try {
            var recipe = recipeRepo.getReferenceById(id);
            recipe.setPic(pic);
            recipeRepo.save(recipe);
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

    }

    public long saveIt(Recipe recipe) {
        var recipeSaved = recipeRepo.save(recipe);

        return recipeSaved.getId();
    }

    public List<Recipe> showAll(){
            EntityManager em = emf.createEntityManager();
            var query = em.createQuery(" FROM Recipe");
            em.getTransaction().begin();
            List list = query.getResultList();
            em.getTransaction().commit();
            return list;

    }

}
