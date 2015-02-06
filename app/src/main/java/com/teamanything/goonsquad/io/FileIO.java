package com.teamanything.goonsquad.io;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Adam on 2/6/2015.
 */
public class FileIO {

    /**
     *
     * @param data the data to write
     * @param context the context of the activity
     */
    private void writeToFile(String filename, String data, Context context) {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/Goonsquad/" + filename);
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(data.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    /**
     *
     * @param filename name of file, starting below /Goonsquad
     * @param context context of the activity
     * @return the data at /Goonsquad/*filename*
     */
    private String readFromFile(String filename, Context context) {

        String ret = "";
        File file = new File(context.getFilesDir().getAbsolutePath() + "/Goonsquad/" + filename);
        byte[] bytes = new byte[(int) file.length()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return new String(bytes);
    }


}
