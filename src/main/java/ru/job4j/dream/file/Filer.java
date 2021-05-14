package ru.job4j.dream.file;

import java.io.File;

public class Filer {
    public static final String IMAGE_FOLDER = "c:\\images\\";

    static public void deleteFile(String fileName) {
        File file = new File(IMAGE_FOLDER + fileName);
        synchronized (IMAGE_FOLDER) {
            file.delete();
        }
    }
}
