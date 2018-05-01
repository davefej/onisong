package com.example.dave.onisong.song;

public class SongHeader{
	
	String title;
	int number;
	Song song;
	
	public SongHeader(String title, int number, Song song) {
		this.title = title; 
		this.number = number; 
		this.song = song;
	}

	public Song getSong() {
		return song;
	}

	public int getNumber(){
		return number;
	}

	public String getTitle(){
		return title;
	}
	
	public String toString() {
		return number+". "+title;
	}
	
}
