package com.bagas.tesbackend.service;

import com.bagas.tesbackend.entity.FormulaGaji;
import com.bagas.tesbackend.model.*;
import com.bagas.tesbackend.repository.FormulaGajiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FormulaGajiService {

    @Autowired
    FormulaGajiRepository formulaGajiRepository;

    public FormulaGajiResponse formulaGajiAdd(FormulaGajiRequest request){

        if (formulaGajiRepository.findFirstByCountry(request.getCountry()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country already registered");
        }

        FormulaGaji formulaGaji = new FormulaGaji();
        formulaGaji.setId(UUID.randomUUID().toString());
        formulaGaji.setCountry(request.getCountry());
        formulaGaji.setTarifPajak(request.getTarifPajak());
        formulaGaji.setTarifPTKP(request.getTarifPTKP());

        formulaGajiRepository.save(formulaGaji);

        FormulaGajiResponse response = new FormulaGajiResponse();
        response.setCountry(formulaGaji.getCountry());
        response.setTarifPajak(formulaGaji.getTarifPajak());
        response.setTarifPTKP(formulaGaji.getTarifPTKP());
        response.setMessage("Sukses disimpan");

        return response;
    }

    public FormulaGajiResponse formulaGajiEdit(FormulaGajiRequest request){

        FormulaGaji formulaGaji = formulaGajiRepository.findFirstByCountry(request.getCountry())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country is not found"));

        formulaGaji.setTarifPTKP(request.getTarifPTKP());
        formulaGaji.setTarifPajak(request.getTarifPajak());
        formulaGajiRepository.save(formulaGaji);

        FormulaGajiResponse response = new FormulaGajiResponse();
        response.setCountry(formulaGaji.getCountry());
        response.setTarifPajak(formulaGaji.getTarifPajak());
        response.setTarifPTKP(formulaGaji.getTarifPTKP());
        response.setMessage("Sukses diedit");

        return response;
    }

    public FormulaGajiResponse formulaGajiDelete(String countryName){

        FormulaGaji formulaGaji = formulaGajiRepository.findFirstByCountry(countryName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country is not found"));

        formulaGajiRepository.delete(formulaGaji);

        FormulaGajiResponse response = new FormulaGajiResponse();
        response.setCountry(formulaGaji.getCountry());
        response.setTarifPajak(formulaGaji.getTarifPajak());
        response.setTarifPTKP(formulaGaji.getTarifPTKP());
        response.setMessage("Sukses dihapus");

        return response;
    }

    public FormulaGajiResponse formulaGajiGetByName(String countryName){

        FormulaGaji formulaGaji = formulaGajiRepository.findFirstByCountry(countryName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country is not found"));


        FormulaGajiResponse response = new FormulaGajiResponse();
        response.setCountry(formulaGaji.getCountry());
        response.setTarifPajak(formulaGaji.getTarifPajak());
        response.setTarifPTKP(formulaGaji.getTarifPTKP());

        return response;
    }

    public List<FormulaGajiList> formulaGajiGetAll(){

        List<FormulaGaji> formulaGaji = formulaGajiRepository.findAll();


        return formulaGaji.stream().map(this::toFormulaList).toList();
    }

    private FormulaGajiList toFormulaList(FormulaGaji formulaGaji) {
        return FormulaGajiList.builder()
                .country(formulaGaji.getCountry())
                .tarifPajak(formulaGaji.getTarifPajak())
                .tarifPTKP(formulaGaji.getTarifPTKP())
                .build();
    }

    public HitungGajiResponse hitungGaji (HitungGajiRequest request){
        FormulaGaji formulaGaji = formulaGajiRepository.findFirstByCountry(request.getCountry())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country is not found"));

        Double gajiBruto = request.getGajiBruto() *12;
        Long tarifPajak = formulaGaji.getTarifPajak();
        Long tarifPTKP = formulaGaji.getTarifPTKP();
        System.out.println(tarifPajak);

        Double gajiDikurangPTKP = gajiBruto - tarifPTKP;

        System.out.println(gajiDikurangPTKP);

        if (gajiBruto < tarifPTKP){
            HitungGajiResponse hitungGajiResponse = new HitungGajiResponse();
            hitungGajiResponse.setName(request.getName());
            hitungGajiResponse.setCountry(request.getCountry());
            hitungGajiResponse.setGajiBruto(gajiBruto.intValue());
            hitungGajiResponse.setPTKP(tarifPTKP.intValue());
            hitungGajiResponse.setGajiNetto((gajiBruto.intValue()));
            hitungGajiResponse.setMessage("Tidak Membayar Pajak. Nominal gaji pertahun yg diperoleh adalah "+gajiBruto.intValue());

            return hitungGajiResponse;
        }
        Double bayarPajak = (double) ((tarifPajak/100.0)*gajiDikurangPTKP);

        System.out.println(bayarPajak);

        Double gajiNetto =  gajiBruto- bayarPajak;



        HitungGajiResponse hitungGajiResponse = new HitungGajiResponse();
        hitungGajiResponse.setName(request.getName());
        hitungGajiResponse.setCountry(request.getCountry());
        hitungGajiResponse.setGajiBruto(gajiBruto.intValue());
        hitungGajiResponse.setPTKP(tarifPTKP.intValue());
        hitungGajiResponse.setPajak(bayarPajak.intValue());
        hitungGajiResponse.setGajiNetto(gajiNetto.intValue());
        hitungGajiResponse.setMessage("Nominal gaji pertahun yg diperoleh adalah "+gajiNetto.intValue());

        return hitungGajiResponse;

    }


}
