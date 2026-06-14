package com.aquienllamo.aquienllamo.model.APIs.Abstract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneValidationResponse {
    private String phone;
    private Boolean valid;
    private String format;
    private String country;
    private String prefix;
}