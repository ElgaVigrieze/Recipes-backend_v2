package com.example.demo.repo;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImpl {
    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public Object findByName(String name){
        EntityManager em = emf.createEntityManager();
        var query = em.createQuery(" FROM User WHERE name =:name");
        query.setParameter("name", name);
        em.getTransaction().begin();
        List list = query.getResultList();
        if(list == null || list.isEmpty() || list.size() == 0){
            return null;
        }
//        System.out.println(list);
        em.getTransaction().commit();
        return list.get(0);
    }
}
