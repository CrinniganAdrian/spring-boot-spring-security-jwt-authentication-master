package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.exceptions.FavouriteNotFound;
import com.bezkoder.springjwt.exceptions.UserNotFound;
import com.bezkoder.springjwt.models.Favourites;
import com.bezkoder.springjwt.repository.FavsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FavsController {

    @Autowired
    private FavsRepository favsRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/favourites")
    List<Favourites> getAllFavourites()
    {
        return favsRepository.findAll();
    }



    @GetMapping("/users")
    List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
    }


    @PostMapping("/favourites")
    Favourites newFavourite(@RequestBody Favourites newFavourite)
    {
        return favsRepository.save(newFavourite);
    }


    @GetMapping("/favourites/{id}")
    Favourites getFavouriteById(@PathVariable Long id) {
        return favsRepository.findById(id)
                .orElseThrow(() -> new FavouriteNotFound(id));
    }

    @DeleteMapping("/favourites/{id}")
    String deleteFavourite(@PathVariable Long id){
        if(!favsRepository.existsById(id)){
            throw new FavouriteNotFound(id);
        }
        favsRepository.deleteById(id);
        return  "Favourite with id "+id+" has been deleted success.";
    }



}
