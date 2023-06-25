package com.bagas.tesbackend.model;

import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private String name;
    private String sex;
    private String maritalStatus;
    private Integer childs;
    private String country;
}
