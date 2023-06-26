package com.bagas.tesbackend.service;

import com.bagas.tesbackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class HitungPajakService {

    @Autowired
    private PajakIndonesiaService pajakDiIndonesia;

    @Autowired
    private PajakVietnamService pajakDiVietnam;

    public HitungPajakResponse hitungPajak(HitungPajakRequest request){

        Employee employeeData = request.getEmployee();
        List<Komponen> komponenGajiData= request.getKomponengaji();

        HitungPajakResponse response= new HitungPajakResponse();

        //cek country indonesia atau vietnam
        if (employeeData.getCountry().equalsIgnoreCase("INDONESIA")){
            response = pajakDiIndonesia.pajakDiIndonesia(komponenGajiData, employeeData);
        }else if (employeeData.getCountry().equalsIgnoreCase("VIETNAM")){
            response = pajakDiVietnam.pajakDiVietnam(komponenGajiData, employeeData);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country name is False");
        }

        return response;

    }

}
