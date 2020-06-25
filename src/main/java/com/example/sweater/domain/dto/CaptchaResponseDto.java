package com.example.sweater.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

//  This simple Java class has a handful of properties and matching data fields.
//  It is annotated with @JsonIgnoreProperties from the Jackson JSON processing library to indicate that any properties
// not bound in this type should be ignored.
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {
    private boolean success;
    @JsonAlias("error-codes")
    private Set<String> error_codes;
}
