package com.connectingDotsInfotech.MoviePage.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(collection="movie")
public class Movie {

	@Id
	private String id;

	private String movieName;
	private Genre genre;				//Make it Enum --> H
	private String duration;
	private Integer year;
	
	@Override
	public String toString()
	{
		return "Movie Name: "+this.movieName+"\nGenre: "+this.genre+"\nDuration: "+this.duration+"\nYera of Realise: "+this.year;
		
	}

}
