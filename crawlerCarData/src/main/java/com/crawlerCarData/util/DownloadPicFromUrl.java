package com.crawlerCarData.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sun.misc.BASE64Encoder;

public class DownloadPicFromUrl {
    //链接url下载图片
    public static void downloadPicture(String urlList, String path) {
        URL url = null;
        try {
            Path rootLocation = Paths.get("car\\log");
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            URI uri = rootLocation.toUri();
            String basePath = uri.toURL().getPath();
            urlList = "http:" + urlList;
            path = basePath + "\\" + path + ".png";
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}