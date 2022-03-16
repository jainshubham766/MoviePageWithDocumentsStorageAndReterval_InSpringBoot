package com.connectingDotsInfotech.MoviePage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.connectingDotsInfotech.MoviePage.domain.Genre;
import com.connectingDotsInfotech.MoviePage.domain.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String>, MovieRepositoryCustom {

	public Movie findByMovieName(String name);

	public List<Movie> findByMovieNameContainingIgnoreCase(String movieName);

	public Page<Movie> findByGenre(Genre g , Pageable page);

}

//find by between 
//gte , gt, lt, lte
//isnot
//in ---> findByYearIn(Array[]int)