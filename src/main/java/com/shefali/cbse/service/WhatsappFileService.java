package com.shefali.cbse.service;

import com.shefali.cbse.model.WhatsappAnalysisResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WhatsappFileService {

    @SneakyThrows
    public WhatsappAnalysisResponse analyseMessages(MultipartFile multipartFile) {

        Map<String, Integer> individualMessageCountMap = new HashMap<>();
        Map<LocalDate, Integer> dateCountMap = new HashMap<>();

        LocalDate dateWithMaxMessages = null;
        int maxNumberOfMessages = Integer.MIN_VALUE;

        int count = 0;

        String string = new String(multipartFile.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        List<String> list = List.of(string.split("\\n"));

        for (String s : list) {

            //30/05/22, 11:26 - Gaurav M Yadav: okay cool
            //19/07/22, 11:50 am - Papa: <Media omitted>

            //11/01/20, 7:46 pm - Gaurav M Yadav: <Media omitted>
            //11/01/20, 11:33 pm - +968 9046 3506: Good

            //TODO : Use Regex To Find If the Message Is Valid New Message Or Not
            if (s.length() > 17 && s.charAt(2) == '/' && s.charAt(5) == '/' && s.charAt(8) == ',') {
                LocalDate dateOfMessage = LocalDate.parse(s.substring(0,8), DateTimeFormatter.ofPattern("dd/MM/yy"));

                String message;
                if(s.indexOf("am") == 15 || s.indexOf("pm") == 15) {
                    message = s.substring(20);
                } else if (s.indexOf("am") == 16 || s.indexOf("pm") == 16) {
                    message = s.substring(21);
                } else {
                    message = s.substring(18);
                }
                int index = message.indexOf(':');
                if (index == -1)
                    continue;

                String name = message.substring(0, index);
                individualMessageCountMap.put(name, individualMessageCountMap.getOrDefault(name, 0) + 1);

                int messagesSentOnThisDay = dateCountMap.getOrDefault(dateOfMessage, 0) + 1;

                if (messagesSentOnThisDay > maxNumberOfMessages) {
                    dateWithMaxMessages = dateOfMessage;
                    maxNumberOfMessages = messagesSentOnThisDay;
                }

                dateCountMap.put(dateOfMessage, messagesSentOnThisDay);

                count++;
            }
        }
        return WhatsappAnalysisResponse.builder()
                .totalMessages(count)
                .groupMembersMessages(individualMessageCountMap)
                .dateWithMaxMessages(dateWithMaxMessages)
                .build();
    }
}
