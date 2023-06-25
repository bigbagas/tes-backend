package com.bagas.tesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitungGajiResponse {

    private String name;
    private String country;
    private Integer gajiBruto;
    private Integer pTKP;
    private Integer pajak;
    private Integer gajiNetto;
    private String message;
}
