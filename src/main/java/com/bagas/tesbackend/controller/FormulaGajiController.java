package com.bagas.tesbackend.controller;

import com.bagas.tesbackend.model.*;
import com.bagas.tesbackend.service.FormulaGajiService;
import com.bagas.tesbackend.service.HitungPajakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FormulaGajiController {

    @Autowired
    private FormulaGajiService formulaGajiService;

    //Test Backend PT CIN
    // Soal Formula Gaji (Middle)

    /* Disediakan API untuk melakukan perubahan formula gaji, diasumsikan rumus gaji adalah gajiNetto = gajibruto - PTKP - pajak
    maka disediakan API untuk melakukan penambahan formula gaji, edit formula gaji, hapus formula gaji, cek formula gaji berdasarkan
    negara, lihat semua formula. Data formula gaji akan disimpan di database kemudian saat API hitung gaji dipanggil maka
    data formula gaji akan di panggil dan akan dipergunakan.

    Diasumsikan untuk matching data karyawan dengan formula gaji menggunakan nama negara asal karyawan
     */

    @PostMapping(
            path = "/formulagaji/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse addFormulaGaji (@RequestBody FormulaGajiRequest request){
        FormulaGajiResponse response = formulaGajiService.formulaGajiAdd(request);

        return WebResponse.builder().data(response).build();

    }

    @PutMapping(
            path = "/formulagaji/edit",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse editFormulaGaji (@RequestBody FormulaGajiRequest request){
        FormulaGajiResponse response = formulaGajiService.formulaGajiEdit(request);

        return WebResponse.builder().data(response).build();

    }

    @DeleteMapping(
            path = "/formulagaji/delete/{countryName}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse deleteFormulaGaji (@PathVariable("countryName") String countryName){
        FormulaGajiResponse response = formulaGajiService.formulaGajiDelete(countryName);

        return WebResponse.builder().data(response).build();

    }

    @GetMapping(
            path = "/formulagaji/get/{countryName}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse getByNameFormulaGaji (@PathVariable("countryName") String countryName){
        FormulaGajiResponse response = formulaGajiService.formulaGajiGetByName(countryName);

        return WebResponse.builder().data(response).build();

    }

    @GetMapping(
            path = "/formulagaji/get/all",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse getAllFormulaGaji (){
        List<FormulaGajiList> response = formulaGajiService.formulaGajiGetAll();

        return WebResponse.builder().data(response).build();

    }

    @PostMapping(
            path = "/formulagaji/hitunggaji",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse hitungGaji (@RequestBody HitungGajiRequest request){
        HitungGajiResponse response = formulaGajiService.hitungGaji(request);

        return WebResponse.builder().data(response).build();

    }


}
