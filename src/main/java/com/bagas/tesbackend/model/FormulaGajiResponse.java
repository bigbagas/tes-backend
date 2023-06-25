package com.bagas.tesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormulaGajiResponse {

    private Long tarifPTKP;
    private Long tarifPajak;
    private String country;
    private String message;

}
