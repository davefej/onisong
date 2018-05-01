package com.example.dave.onisong.download;

import android.content.Context;

import com.example.dave.onisong.DownloadActivity;
import com.example.dave.onisong.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dave on 2018. 01. 16..
 */

public class Download {

    DownloadActivity da;
    public boolean downloadSongs(final DownloadActivity da){
        this.da = da;
        new Thread() {
            public void run() {
                if(downloadFile("http://davidanddavid.hu/oni/enekek.txt")){
                    da.success();
                }else{
                    da.error();
                }
            }
        }.start();
        return true;
    }

    private boolean downloadFile(final String path)
    {
        try
        {
            URL url = new URL(path);
            URLConnection ucon = url.openConnection();
            ucon.setReadTimeout(4000);
            ucon.setConnectTimeout(8000);
            InputStream is = ucon.getInputStream();
            BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
            FileOutputStream outStream = da.openFileOutput("enekek.txt",Context.MODE_PRIVATE);
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
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
