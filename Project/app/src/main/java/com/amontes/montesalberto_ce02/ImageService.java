// Alberto Montes
// MDF3 1604
// CE02

package com.amontes.montesalberto_ce02;

import android.app.IntentService;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageService extends IntentService{

    OutputStream os;
    InputStream is;

    private final String[] IMAGES = {

                "MgmzpOJ.jpg", "VZmFngH.jpg", "ptE5z9u.jpg",
                "4QKO8Up.jpg", "Vm2UdDH.jpg", "C040ctB.jpg",
                "MScR8za.jpg", "tM1bsAH.jpg", "fS1lKZx.jpg",
                "h8e5rBX.jpg", "KBtUxzq.jpg", "wYXWJZz.jpg",
                "LOUwRC4.jpg", "7ZSQfIu.jpg", "XLJiKqp.jpg",
                "nXVLE9W.jpg", "HYQuj4b.jpg", "R8YIb8d.jpg",
                "cLv3TVc.jpg", "f7pMMdA.jpg", "Dl1aIHV.jpg",
                "UE3ng26.jpg", "1oyYfr0.jpg", "YSJ28fr.jpg",
                "Ey39hl5.jpg", "HAnhjCI.jpg", "En3J4ZF.jpg",
                "wr65Geg.jpg", "7D35kbV.jpg", "Z2WQBPI.jpg"

    };


    // Default constructor.
    public ImageService() {

        // Name thread.
        super("ImageService");

    }

    // "Background" operations.
    @Override
    protected void onHandleIntent(Intent intent) {

        // Loop through array of images and broadcast/save.
        for(int i = 0; i < IMAGES.length; i++){

            try{

                String URL_BASE = "http://i.imgur.com/";

                URL url = new URL(URL_BASE +IMAGES[i]);
                HttpURLConnection httpConnect = (HttpURLConnection)url.openConnection();
                httpConnect.connect();
                is = url.openStream();

                // Create file path to /storage/emulated/0/Android/data.
                File sdCard = getExternalFilesDir("com.montesalberto_ce02");
                File directory = new File (sdCard + "/Lab2Data");

                // Create File if it does not exist.
                if(!(directory.exists())) {

                    directory.mkdirs();

                }

                // Load saved files if directory is already full.
                if(!(directory.listFiles() == null)) {
                    if (directory.listFiles().length == IMAGES.length) {

                        Intent finalBroadcast = new Intent("com.amontes.montesalberto_ce02.UPDATE_UI");
                        sendBroadcast(finalBroadcast);
                        break;

                    }
                }

                File file = new File(directory, IMAGES[i]);
                os = new FileOutputStream(file);
                int buffLength;
                byte[] buffer = new byte[2048];

                while ((buffLength = is.read(buffer)) != -1) {

                    os.write(buffer, 0, buffLength);

                }

                // Close Input/Output streams when necessary.
                if(!(is == null)) {

                    is.close();

                }

                if(!(os == null)) {

                    os.close();

                }

                // Broadcast to MainActivity, letting it know to update UI after each image is saved.
                Intent broadcastImage = new Intent("com.amontes.montesalberto_ce02.UPDATE_UI");
                sendBroadcast(broadcastImage);


            } catch (IOException e) {

                e.printStackTrace();

            }


        }

    }

}