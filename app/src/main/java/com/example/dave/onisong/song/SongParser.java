package com.example.dave.onisong.song;
import android.content.Context;
import android.widget.Toast;

import com.example.dave.onisong.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SongParser {

	static final String FILENAME = "enekek.txt";
	
	public static int parseSongFile(Context ctx){

		int count = 0;
		try {
			FileInputStream fin = ctx.openFileInput(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			count = parse(ctx,br);
			br.close();
			return count;
		} catch (Exception e) {
			TableOfContents.getInstance().reset();
			e.printStackTrace();
		}

		InputStream is = ctx.getResources().openRawResource(R.raw.enekek);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			count = parse(ctx,br);
			return count;
		} catch (IOException e) {
			Toast.makeText(ctx,"Valami balul sült el :(",Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return -1;
	}

	public static boolean hasSongFile(Context ctx){
		try {
			FileInputStream fin = ctx.openFileInput(SongParser.FILENAME);
			fin.close();
			return true;
		} catch (Exception e) {
			TableOfContents.getInstance().reset();
			e.printStackTrace();
		}
		return false;
	}

	public static void moveDefaultSongToStorage(Context ctx) throws IOException {
		InputStream inStream = ctx.getResources().openRawResource(R.raw.enekek);
		FileOutputStream outStream = ctx.openFileOutput(FILENAME,Context.MODE_PRIVATE);
		byte[] buff = new byte[5 * 1024];
		int len;
		while ((len = inStream.read(buff)) != -1)
		{
			outStream.write(buff, 0, len);
		}
		outStream.flush();
		outStream.close();
		inStream.close();
	}

	public static String getSongFile(Context ctx) throws IOException{
		BufferedReader br;
		try{
			FileInputStream fin = ctx.openFileInput("enekek.txt");
			br = new BufferedReader(new InputStreamReader(fin));
		}catch(IOException e){
			InputStream is = ctx.getResources().openRawResource(R.raw.enekek);
			br = new BufferedReader(new InputStreamReader(is));
		}

		String line;
		StringBuilder songs = new StringBuilder();
		while ((line = br.readLine()) != null) {
			songs.append(line+"\n");
		}
		return songs.toString();
	}

	private static int parse(Context ctx,BufferedReader br ) throws IOException {
		int i = 0;
		String line;
		Song song = null;
		List<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			if(line.contains("#")) {
				String[] parts = line.split("#");
				int num = Integer.parseInt(parts[0]);
				song = new Song(num,parts[1]);
				i++;
			}else if(line.trim().isEmpty()) {
				if(lines.size() > 0) {
					song.addVerse(lines);
				}

				lines = new ArrayList<String>();
			}else {
				lines.add(line);
			}
		}
		br.close();
		return i;
	}

}
