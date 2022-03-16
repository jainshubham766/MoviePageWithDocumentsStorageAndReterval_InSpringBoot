package com.connectingDotsInfotech.MoviePage.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.connectingDotsInfotech.MoviePage.domain.Genre;
import com.connectingDotsInfotech.MoviePage.domain.Movie;
import com.connectingDotsInfotech.MoviePage.domain.MovieArray;

public class MovieRepositoryImpl implements MovieRepositoryCustom {

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Movie addMovie(Movie movie) {
		Movie response = movieRepository.save(movie);
		return response;
	}

	@Override
	public ResponseEntity<List<Movie>> getAllMovie() {
		List<Movie> reponseList = movieRepository.findAll();
		return new ResponseEntity<List<Movie>>(reponseList, HttpStatus.OK);
	}

	@Override
	public Movie getMovie(String id) {
		Movie result = movieRepository.findById(id).orElse(null);
		return result;
	}

	@Override
	public List<Movie> searchMovie(String movieName) {
		// TODO Auto-generated method stub
		return movieRepository.findByMovieNameContainingIgnoreCase(movieName);
		// return null;
	}

	@Override
	public String deleteMovie(String id) {
		// TODO Auto-generated method stub
		movieRepository.deleteById(id);
		return "Deleted Sucessfully!";
	}

	@Override
	public Movie updateMovie(String id) {
		Movie movie = movieRepository.findById(id).orElse(null);
		movie.setMovieName("None");
		return movieRepository.save(movie);
	}

	@Override
	public Page<Movie> getAllMoviesPage(Pageable page) {
		Page<Movie> result = movieRepository.findAll(page);
		return result;
	}

	@Override
	public Page<Movie> getMoviesByGenrePage(Pageable page, Genre genre) {
		Page<Movie> result = movieRepository.findByGenre(genre, page);
		return result;
	}

	@Override
	public List<Movie> getMoviesTemplateByYearGenre(Integer year, Genre genre) {
		Criteria criteria = Criteria.where("year").is(year).and("genre").is(genre); // condition
		Query query = new Query(criteria); // Query will execute your condition
		return mongoTemplate.find(query, Movie.class); // execute your query
	}

	@Override
	public Page<Movie> getMoviesTemplatePageByYearGenre(Pageable page, Integer year, Genre genre) {
		Criteria criteria = Criteria.where("year").is(year).and("genre").is(genre);
		Query query = new Query(criteria).with(page);

		return new PageImpl<>(mongoTemplate.find(query, Movie.class)); // list--> page
	}

	@Override
	public List<Movie> getMovieByYear(Genre genre) {

		Criteria criteria = Criteria.where("genre").is(genre);
		MatchOperation matchOperation = Aggregation.match(criteria);

		SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "year");

		Aggregation aggregation = Aggregation.newAggregation(matchOperation, sortOperation);
		System.out.println(aggregation.toString());
		List<Movie> response = mongoTemplate.aggregate(aggregation, "movie", Movie.class).getMappedResults();
		// ----------Aggregate class return type-----------------|-----List of
		// theaggregated Result---|
		return response;
	}

	// Trial 01
	@Override
	public List<MovieArray> getMovieByGenre() {
		GroupOperation groupOperation = Aggregation.group("genre").push("$$ROOT").as("data");

		SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "_id");

		Aggregation aggregation = Aggregation.newAggregation(groupOperation, sortOperation);

		System.out.println(aggregation.toString());
		List<MovieArray> response = mongoTemplate.aggregate(aggregation, "movie", MovieArray.class).getMappedResults();

		return response;
	}

}
