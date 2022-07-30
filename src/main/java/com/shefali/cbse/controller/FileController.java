package com.shefali.cbse.controller;

import com.shefali.cbse.model.FileUploadResponse;
import com.shefali.cbse.model.School;
import com.shefali.cbse.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileUploadResponse uploadExaminationFile(@RequestPart MultipartFile multipartFile,
                                                    @RequestParam String emailAddress,
                                                    @RequestParam School school) {
        return fileService.storeFile(multipartFile, school, emailAddress);
    }

    @GetMapping("/shefali")
    public String helloShefali(@RequestParam String name) {
        return "Hello" + name;
    }
}
