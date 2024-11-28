package org.mh.phd.dataextractionservice.controller;

import org.mh.phd.dataextractionservice.service.ExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExtractionController {


    private final ExtractionService service;
    @Autowired
    public ExtractionController(ExtractionService service) {
        this.service = service;
    }

    @PostMapping("/process-image")
    public Mono<Map> process(@RequestParam("image") MultipartFile imageFile) throws IOException {
        // Convert MultipartFile to File
        File tempFile = File.createTempFile("uploaded", ".tmp");
        imageFile.transferTo(tempFile);

        // Call the service method to upload the image
        return service.getDataFromImage(tempFile)
                .doFinally(signalType -> tempFile.delete()); // Clean up temp file after upload
    }
}
