package com.example.dave.onisong.song;
import java.util.ArrayList;
import java.util.List;

public class Song {

	String title;
	List<Verse> verses;
	SongHeader songheader;
	
	class Verse{
		List<String> lines;		
		Verse(List<String> lines){
			this.lines = lines;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(String line:lines) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();
		}
	}

	Song(int number,String title){
		songheader = new SongHeader(title,number,this);
		verses = new ArrayList<Verse>();
		TableOfContents.getInstance().add(songheader);
	}


	public void addVerse(List<String> lines) {
		verses.add(new Verse(lines));		
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Verse v : verses) {
			sb.append(v.toString());
			sb.append("\r\n");
		}
		return sb.toString();
	}
	
}

