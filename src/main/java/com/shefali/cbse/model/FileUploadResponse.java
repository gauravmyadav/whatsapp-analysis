package com.shefali.cbse.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileUploadResponse {
    private int filesUploaded;
    private int totalFilesRequired;
}
