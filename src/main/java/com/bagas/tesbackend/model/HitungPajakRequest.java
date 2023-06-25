package com.bagas.tesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitungPajakRequest {

    private Employee employee;
    private List<Komponen> komponengaji;
}
