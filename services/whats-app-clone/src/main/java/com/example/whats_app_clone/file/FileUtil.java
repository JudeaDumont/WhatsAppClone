package com.example.whats_app_clone.file;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtil {

    private FileUtil(){}

    public static byte[] readFile(String path){
        if(StringUtils.isBlank(path)){
            return new byte[0];
        }
        try {
            Path file = new File(path).toPath();
            return Files.readAllBytes(file);
        } catch (IOException e) {
            log.warn("No such file: {}", path);
        }
        return new byte[0];
    }
}
