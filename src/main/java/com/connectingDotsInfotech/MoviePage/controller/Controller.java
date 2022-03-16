package com.connectingDotsInfotech.MoviePage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.connectingDotsInfotech.MoviePage.domain.Genre;
import com.connectingDotsInfotech.MoviePage.domain.Movie;
import com.connectingDotsInfotech.MoviePage.domain.MovieArray;
import com.connectingDotsInfotech.MoviePage.repository.MovieRepository;

@org.springframework.stereotype.Controller
@RequestMapping(path = "/movie")
public class Controller {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(Controller.class);

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Movie> add(@RequestBody Movie movie) {
		Movie result = movieRepository.addMovie(movie);
		return new ResponseEntity<Movie>(result, HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/getAll")
	public ResponseEntity<List<Movie>> getAll() {
		System.out.println("Getting Movies");
		LOG.info("Getting Movies");
		String str = "Hello World";
		LOG.info("Data is {}", str);
		return movieRepository.getAllMovie();
	}

	@GetMapping(path = "/get/{id}")
	public ResponseEntity<Movie> get(@PathVariable String id) {
		Movie response = movieRepository.getMovie(id);
		return new ResponseEntity<Movie>(response, HttpStatus.OK);
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		String response = movieRepository.deleteMovie(id);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/search/{movieName}")
	public ResponseEntity<List<Movie>> searchMovie(@PathVariable String movieName) {
		List<Movie> response = movieRepository.searchMovie(movieName);
		return new ResponseEntity<List<Movie>>(response, HttpStatus.OK);
	}

	@PutMapping(path = "/updateMovie/{id}")
	public ResponseEntity<Movie> updateMovieInfo(@PathVariable String id) {
		Movie response = movieRepository.updateMovie(id);
		return new ResponseEntity<Movie>(response, HttpStatus.ACCEPTED);

	}

	// Pageable API's
	@GetMapping(path = "/all/page")
	public ResponseEntity<Page<Movie>> getAllPage(Pageable page) {
		Page<Movie> response = movieRepository.getAllMoviesPage(page);
		return new ResponseEntity<Page<Movie>>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/genre/page/{genre}")
	public ResponseEntity<Page<Movie>> getMovieByGenre(Pageable page, @PathVariable Genre genre) {
		Page<Movie> response = movieRepository.getMoviesByGenrePage(page, genre);
		return new ResponseEntity<Page<Movie>>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/getByTemplate")
	public ResponseEntity<List<Movie>> getTemplate(@RequestParam Integer year, @RequestParam Genre genre) {
		List<Movie> mt = movieRepository.getMoviesTemplateByYearGenre(year, genre);
		// Above movieRepository contains mongoTemplate function in custom class
		return new ResponseEntity<List<Movie>>(mt, HttpStatus.OK);
	}

	@GetMapping(path = "/getByTemplate/page")
	public ResponseEntity<Page<Movie>> getTemplatePage(Pageable page, @RequestParam Integer year,
			@RequestParam Genre genre) {
		Page<Movie> mt = movieRepository.getMoviesTemplatePageByYearGenre(page, year, genre);
		// type Casting List to Page in the above method
		return new ResponseEntity<Page<Movie>>(mt, HttpStatus.OK);
	}

	@GetMapping(path = "/getByTemplate/match")
	public ResponseEntity<List<Movie>> getTemplateMatch(@RequestParam Genre genre) {
		List<Movie> mt = movieRepository.getMovieByYear(genre);

		return new ResponseEntity<List<Movie>>(mt, HttpStatus.OK);
	}

	@GetMapping(path = "/getByTemplate/genreSublistYear")
	public ResponseEntity<List<MovieArray>> getTemplateGenre() {
		List<MovieArray> mt = movieRepository.getMovieByGenre();

		return new ResponseEntity<List<MovieArray>>(mt, HttpStatus.OK);
	}

}

//flow: 	client--> server--> db-->queries--> (transfer) back to server-->response to client