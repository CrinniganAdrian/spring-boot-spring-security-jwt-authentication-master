package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavsRepository extends JpaRepository <Favourites,Long>  {

}
