package com.bagas.tesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitungGajiRequest {

    private String name;
    private String country;
    private Double gajiBruto;
}
