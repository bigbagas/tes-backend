package com.bagas.tesbackend.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitungPajakResponse {

    private Integer pajakPerbulan;
    private Double gajiSetahun;
    private String message;
}
