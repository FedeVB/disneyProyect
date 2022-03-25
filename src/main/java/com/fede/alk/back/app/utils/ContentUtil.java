package com.fede.alk.back.app.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ContentUtil {

    public static byte[] devolverContent(MultipartFile imagen) {

        if (!imagen.isEmpty()) {
            if (imagen.getContentType().endsWith("jpg") || imagen.getContentType().endsWith("png")
                    || imagen.getContentType().endsWith("jpeg")) {
                try {
                    return imagen.getBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new byte[0];
    }
}
