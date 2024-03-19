package com.zgamelogic.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class FileDownloadController {

    private File downloadFolder;

    @PostConstruct
    public void initialize() {
        downloadFolder = new File("downloads");
        log.info("Download folder exists: {}", downloadFolder.exists());
        if(downloadFolder.exists()){
            for(File f: downloadFolder.listFiles()){
                log.info(f.getName());
            }
        }
    }

    @GetMapping("/downloads/**")
    public ResponseEntity<Resource> download(HttpServletRequest request){
        String fileName = request.getRequestURI().replaceFirst("/downloads/", "");
        if(!new File("file:" + downloadFolder + "//" + fileName).exists()){
            return ResponseEntity.notFound().build();
        }
        // Load file as Resource
        Resource resource = null;
        try {
            resource = new UrlResource("file:" + downloadFolder + "//" + fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .header("Contend-Description", "File Transfer")
                .header("Content-type", "application/octet-stream")
                .header("Expires", "0")
                .body(resource);
    }
}