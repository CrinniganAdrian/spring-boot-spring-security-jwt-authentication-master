package com.bezkoder.springjwt.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "favourite_items",
            joinColumns = @JoinColumn(name = "favourite_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Favourites> favourites = new HashSet<>();


    public Favourites() {

    }

    public Favourites(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Favourites> getFavourites() {
        return favourites;
    }

    public void setFavourites(Set<Favourites> favourites) {
        this.favourites = favourites;
    }
}