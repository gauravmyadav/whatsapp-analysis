package com.shefali.cbse.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Data
public class WhatsappAnalysisResponse {

    private int totalMessages;

    private Map<String, Integer> groupMembersMessages;

    private LocalDate dateWithMaxMessages;
}
