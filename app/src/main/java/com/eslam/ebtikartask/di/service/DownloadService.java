package com.eslam.ebtikartask.di.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {

    public static final int UPDATE_PROGRESS = 8344;
    String folder_main = "ImagesFolder";


    public DownloadService() {
        super("DownloadService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        String urlToDownload = intent.getStringExtra("url");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");

        try {

            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();


            InputStream input = new BufferedInputStream(connection.getInputStream());


            File outerFolder = new File(Environment.getExternalStorageDirectory(), folder_main);
            File inerDire = new File(outerFolder.getAbsoluteFile(), System.currentTimeMillis() + ".jpg");


            if (!outerFolder.exists()) {
                outerFolder.mkdirs();
            }

            inerDire.createNewFile();


            FileOutputStream output = new FileOutputStream(inerDire);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;

                Bundle resultData = new Bundle();
                resultData.putInt("progress", (int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
             Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("** ex", e.toString());
         }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);

        receiver.send(UPDATE_PROGRESS, resultData);
    }


}