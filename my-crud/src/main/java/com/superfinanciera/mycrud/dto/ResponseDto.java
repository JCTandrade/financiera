package com.superfinanciera.mycrud.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto {
    private Object mensaje;
    private boolean isError;
    private HttpStatus status;
}
