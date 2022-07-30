package com.shefali.cbse.service;

import com.shefali.cbse.model.FileUploadResponse;
import com.shefali.cbse.model.School;
import com.shefali.cbse.model.WhatsappAnalysisResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class FileService {

    private final MailService mailService;
    private final String fileUploadDirectory;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final Map<School, String> emailMap = new HashMap<>();
    private final int requiredCount = 4;
    private final List<School> list = new LinkedList<>();
    private boolean mailsToBeSent = true;

    public FileService(@Value("${file.upload-dir}") String fileUploadDirectory,
                       MailService mailService) {
        this.fileUploadDirectory = fileUploadDirectory;
        this.mailService = mailService;
    }

    @PostConstruct
    public void init() {
        list.addAll(Arrays.asList(School.values()));
    }

    @SneakyThrows
    public FileUploadResponse storeFile(MultipartFile multipartFile, School school, String emailAddress) {
        String fileName = "\\" + school + ".txt";
        Path path = Path.of(fileUploadDirectory + fileName);
        Files.deleteIfExists(path);
        log.info(String.valueOf(path));

        Files.copy(multipartFile.getInputStream(),
                path, StandardCopyOption.REPLACE_EXISTING);

        if (!emailMap.containsKey(school)) {
            atomicInteger.incrementAndGet();
        }
        emailMap.put(school, emailAddress);

        mailService.sendMessageWithAttachment(emailAddress, "Question Paper Uploaded By You", "Hi " + school, fileUploadDirectory + fileName);

        if (atomicInteger.get() == requiredCount && mailsToBeSent) {
            emailMap.forEach((k, v) -> {
                School randomSchool = getRandomSchool(k);
                String randomFileName = fileUploadDirectory + "\\" + randomSchool + ".txt";
                log.info("Random School {}", randomFileName);
                mailService.sendMessageWithAttachment(v, "Your Question Paper", "Hi " + k, randomFileName);
            });
            mailsToBeSent = false;
        }

        log.info("File No: {} Uploaded Successfully", atomicInteger.get());

        return createResponse();
    }

    private School getRandomSchool(School school) {
        while (true) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(list.size());
            School randomElement = list.get(randomIndex);
            if (randomElement == school) {
                continue;
            }
            list.remove(randomIndex);
            return randomElement;
        }
    }

    private FileUploadResponse createResponse() {
        return FileUploadResponse.builder()
                .filesUploaded(atomicInteger.get())
                .totalFilesRequired(requiredCount)
                .build();
    }
}
