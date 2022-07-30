package com.shefali.cbse.controller;

import com.shefali.cbse.model.WhatsappAnalysisResponse;
import com.shefali.cbse.service.FileService;
import com.shefali.cbse.service.WhatsappFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/whatsappfile")
@AllArgsConstructor
public class WhatsappFileController {

    private final WhatsappFileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WhatsappAnalysisResponse uploadExaminationFile(@RequestPart MultipartFile multipartFile) {
        return fileService.analyseMessages(multipartFile);
    }
}
