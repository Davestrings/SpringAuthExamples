package com.example.auth.authentications.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    @Min(value = 1, message = "Paging must start be at least 1")
    private long number = 1;

    @Min(value = 1, message = "You can request a minimum of 1 record ")
    @Max(value = 100, message = "You can request maximum of 100 records")
    private long limit = 10;
}
