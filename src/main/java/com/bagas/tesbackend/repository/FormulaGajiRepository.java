package com.bagas.tesbackend.repository;

import com.bagas.tesbackend.entity.FormulaGaji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormulaGajiRepository extends JpaRepository<FormulaGaji, String> {

    Optional<FormulaGaji>findFirstByCountry(String countryName);
}
