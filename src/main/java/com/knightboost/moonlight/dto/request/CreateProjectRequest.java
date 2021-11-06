package com.knightboost.moonlight.dto.request;

import lombok.Data;

@Data
public class CreateProjectRequest {
    private long origination;
    private String projectName;
    private String platform;
}
