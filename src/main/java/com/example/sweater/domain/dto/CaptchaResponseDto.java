package com.example.sweater.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

//  This simple Java class has a handful of properties and matching data fields.
//  It is annotated with @JsonIgnoreProperties from the Jackson JSON processing library to indicate that any properties
// not bound in this type should be ignored.
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {
    private boolean success;
    @JsonAlias("error-codes")
    private Set<String> error_codes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getError_codes() {
        return error_codes;
    }

    public void setError_codes(Set<String> error_codes) {
        this.error_codes = error_codes;
    }
}
