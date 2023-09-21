package com.zgamelogic.controllers;

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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@Slf4j
public class MavenRepoController {

    private File mavenRepo;

    @PostConstruct
    public void initialize() {
        mavenRepo = new File("maven");
        if (!mavenRepo.exists()) {
            log.info("Creating maven directory");
            mavenRepo.mkdirs();
        }
    }

    @GetMapping("/maven/**")
    public ResponseEntity<Resource> download(HttpServletRequest request){
        String fileName = request.getRequestURI().replaceFirst("/maven/", "");
        // Load file as Resource
        Resource resource = null;
        try {
            resource = new UrlResource("file:" + mavenRepo + "//" + fileName);
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