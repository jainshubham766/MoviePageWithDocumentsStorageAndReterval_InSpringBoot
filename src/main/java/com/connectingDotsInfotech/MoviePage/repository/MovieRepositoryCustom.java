package com.connectingDotsInfotech.MoviePage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.connectingDotsInfotech.MoviePage.domain.Genre;
import com.connectingDotsInfotech.MoviePage.domain.Movie;
import com.connectingDotsInfotech.MoviePage.domain.MovieArray;

public interface MovieRepositoryCustom {

	public Movie addMovie(Movie movie);

	public ResponseEntity<List<Movie>> getAllMovie();

	public Movie getMovie(String id);

	public List<Movie> searchMovie(String movieName);

	public String deleteMovie(String id);

	public Movie updateMovie(String id);

	public Page<Movie> getAllMoviesPage(Pageable page);

	public Page<Movie> getMoviesByGenrePage(Pageable page, Genre genre);

	public List<Movie> getMoviesTemplateByYearGenre(Integer year, Genre genre);

	public Page<Movie> getMoviesTemplatePageByYearGenre(Pageable page, Integer year, Genre genre);

	public List<Movie> getMovieByYear(Genre genre);

	public List<MovieArray> getMovieByGenre();

}
