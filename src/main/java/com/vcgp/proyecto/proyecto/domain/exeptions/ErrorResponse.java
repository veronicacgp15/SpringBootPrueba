package com.vcgp.proyecto.proyecto.domain.exeptions;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<ValidationError> details;

 }

@Data
class ValidationError {
    private String field;
    private String rejectedValue;
    private String message;
}
