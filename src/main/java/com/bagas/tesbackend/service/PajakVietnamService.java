package com.bagas.tesbackend.service;

import com.bagas.tesbackend.model.Employee;
import com.bagas.tesbackend.model.HitungPajakResponse;
import com.bagas.tesbackend.model.Komponen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PajakVietnamService {

    public HitungPajakResponse pajakDiVietnam (List<Komponen> komponenGajiData, Employee employeeData){


        Double earning = 0.0;
        Double deduction = 0.0;
        Double insurance = 0.0;
        Double pTKP = 0.0;
        Double pajakPerbulan=0.0;
        Double gajiPertahun = 0.0;
        Double gajiPerbulan = 0.0;
        Double penghasilanNetto = 0.0;

        for (int i = 0; i < komponenGajiData.size(); i++) {

            Komponen komponen = komponenGajiData.get(i);
            if (komponen.getType().equalsIgnoreCase("earning")){
                earning = earning +komponen.getAmount();
            }

            if (komponen.getType().equalsIgnoreCase("deduction") && !komponen.getName().contains("insurance")){
                deduction = deduction+komponen.getAmount();
            }

            if (komponen.getType().equalsIgnoreCase("deduction") &&komponen.getName().contains("insurance")){
                insurance = insurance +komponen.getAmount();
            }
        }

        System.out.println("earning = "+ earning);
        System.out.println("deduction = "+ deduction);
        System.out.println("insurance = "+ insurance);

        pTKP = menghitungPTKPVietnam(employeeData);

        gajiPerbulan = earning - deduction;
        gajiPertahun = (gajiPerbulan * 12);
        Double asuransi = insurance *12;
        System.out.println("gajiPerbulan = "+ gajiPerbulan);
        System.out.println("gajiPertahun = "+gajiPertahun);
        System.out.println("asuransi pertahun= "+asuransi);

        if (gajiPertahun <= pTKP){
            HitungPajakResponse response = new HitungPajakResponse();
            response.setMessage("Tidak Membayar Pajak");
            return response;
        }

        penghasilanNetto = ((gajiPertahun - asuransi)- pTKP)/1000000;
        pajakPerbulan = menghitungPajakVietnam(penghasilanNetto);
        HitungPajakResponse response= new HitungPajakResponse();

        int angkaSignifikasi = 2;
        Double temp = Math.pow(10,angkaSignifikasi);
        Double pembulatan = Math.round(pajakPerbulan*temp)/temp;

        response.setPajakPerbulan((int) (pembulatan*1000000));
        response.setGajiSetahun( penghasilanNetto);
        response.setMessage("Membayar Pajak");
        return response;
    }

    //menghitung PTKP di vietnam
    private Double menghitungPTKPVietnam(Employee employeeData){
        Double pTKPVietnam =0.0;

        if (!employeeData.getMaritalStatus().equalsIgnoreCase("maried")){
            pTKPVietnam = + 15000000.0;
        }else {
            pTKPVietnam=+30000000.0;
        }

        System.out.println("PTKP Vietnam = "+pTKPVietnam);
        return pTKPVietnam;
    }

    //menghitung pajak dng aturan vietnam
    private Double menghitungPajakVietnam(Double penghasilanNetto){
        Double pajakLayer1 = 0.0;
        Double pajakLayer2 = 0.0;
        Double pajakSetahun = 0.0;
        System.out.println("penghasilanNetto = "+penghasilanNetto);

        //nilai dalam jutaan
        if (penghasilanNetto <= 50){
            pajakSetahun = penghasilanNetto*0.025;
        }else {
            pajakLayer1 = 1.25;
            pajakLayer2 = (penghasilanNetto - 50)*0.075;
            pajakSetahun = pajakLayer1 + pajakLayer2;
        }
        System.out.println("pajak1 = "+ pajakLayer1);
        System.out.println("pajak2 = "+ pajakLayer2);
        System.out.println("pajakSetahun = "+ pajakSetahun);

        Double pajakPerbulan = pajakSetahun/12;
        System.out.println("pajakPerbulan = "+pajakPerbulan);
        return pajakPerbulan;
    }
}
