package com.example.dave.onisong.song;
import java.util.ArrayList;
import java.util.List;


public class TableOfContents {

	TableOfContents filtered;
	List<SongHeader> songs;
	String lastfilter = "";
	private static TableOfContents instance = null;

	protected TableOfContents() {
		songs = new ArrayList<SongHeader>();
	}
	
	public static TableOfContents getInstance() {
		if(instance == null) {
			instance = new TableOfContents();
		}
		return instance;
	}
	
	public void add(SongHeader sh) {
		songs.add(sh);
	}

	public SongHeader get(int i){
		return songs.get(i);
	}

	public int size(){
		return songs.size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(SongHeader sh : songs) {
			sb.append("\r\n\r\n----\r\n\r\n");
			sb.append(sh.toString());
			sb.append("\r\n");
			sb.append(sh.getSong().toString());
		}
		return sb.toString();
	}

    public void reset() {
		songs = new ArrayList<SongHeader>();
    }

    public TableOfContents filtered(String filter) {
		if(lastfilter == filter){
			return filtered;
		}
		lastfilter = filter;
		filtered = new TableOfContents();
		for (SongHeader sh: songs) {
			if(sh.getTitle().toLowerCase().contains(filter.toLowerCase()) || (sh.getNumber()+"").contains(filter.trim())){
				filtered.add(sh);
			}
		}
		return filtered;
    }
}
