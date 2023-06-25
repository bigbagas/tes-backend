package com.bagas.tesbackend.service;

import com.bagas.tesbackend.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class HitungPajakService {

    public HitungPajakResponse hitungPajak(HitungPajakRequest request){

        Employee employeeData = request.getEmployee();
        List<Komponen> komponenGajiData= request.getKomponengaji();

        Double earning = 0.0;
        Double deduction = 0.0;
        Double insurance = 0.0;
        Double pTKP = 0.0;
        Double pajakPerbulan=0.0;
        Double gajiPertahun = 0.0;
        Double gajiPerbulan = 0.0;
        Double penghasilanNetto = 0.0;

        //cek country indonesia atau vietnam
        if (employeeData.getCountry().equalsIgnoreCase("INDONESIA")){

            for (int i = 0; i < komponenGajiData.size(); i++) {
                Komponen komponen = komponenGajiData.get(i);
                System.out.println(komponen.getName());
                if (komponen.getType().equalsIgnoreCase("earning")){
                    earning = earning+komponen.getAmount();
                }else {
                    deduction = deduction+komponen.getAmount();
                }
            }

            System.out.println("earning = "+earning);
            System.out.println("deduction = "+deduction);

            //cek PTKP Indonesia
            pTKP = menghitungPTKPIndonesia(employeeData);

            gajiPerbulan = earning - deduction;
            gajiPertahun = (gajiPerbulan * 12);
            System.out.println("gajiPerbulan = "+gajiPerbulan);
            System.out.println("gajiPertahun = "+gajiPertahun);

            if (gajiPertahun <= pTKP){
                HitungPajakResponse response = new HitungPajakResponse();
                response.setMessage("Tidak Membayar Pajak");

//                WebResponse webResponse = new WebResponse();
//                webResponse.setData(response);
                return response;
            }

             penghasilanNetto = (gajiPertahun - pTKP)/1000000;

            // hitung pajak perbulan di indonesia
            pajakPerbulan = menghitungPajakIndonesia(penghasilanNetto);

        }else if (employeeData.getCountry().equalsIgnoreCase("VIETNAM")){
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

//                WebResponse webResponse = new WebResponse();
//                webResponse.setData(response);

                return response;


            }


             penghasilanNetto = ((gajiPertahun - asuransi)- pTKP)/1000000;
//            Double penghasilanNet = penghasilanNetto /1000000;



            pajakPerbulan = menghitungPajakVietnam(penghasilanNetto);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country name is False");
        }

        HitungPajakResponse response= new HitungPajakResponse();

        int angkaSignifikasi = 2;
        Double temp = Math.pow(10,angkaSignifikasi);
        Double pembulatan = Math.round(pajakPerbulan*temp)/temp;

        response.setPajakPerbulan((int) (pembulatan*1000000));
        response.setGajiSetahun( penghasilanNetto);
        response.setMessage("Membayar Pajak");
//
//        WebResponse webResponse = new WebResponse();
//        webResponse.setData(response);


        return response;

    }

    //menghitung PTKP di Indonesia
    private Double menghitungPTKPIndonesia(Employee employeeData){
        Double pTKPIndonesia =0.0;

        if (!employeeData.getMaritalStatus().equalsIgnoreCase("maried") && employeeData.getChilds().equals(0)){
            pTKPIndonesia = + 25000000.0;
        }else if (employeeData.getMaritalStatus().equalsIgnoreCase("maried") && employeeData.getChilds().equals(0)) {
            pTKPIndonesia = + 50000000.0;
        }else {
            pTKPIndonesia=+75000000.0;
        }

        System.out.println("PTKP Indonesia = "+pTKPIndonesia);
        return pTKPIndonesia;
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

    //Menghitung pajak dng aturan Indonesia
    private Double menghitungPajakIndonesia(Double penghasilanNetto){

        Double pajakLayer1 = 0.0;
        Double pajakLayer2 = 0.0;
        Double pajakLayer3 = 0.0;
        Double pajakSetahun = 0.0;

        System.out.println("penghasilanNetto = "+penghasilanNetto);

        //nilai dalam jutaan
        if (penghasilanNetto <= 50 && penghasilanNetto >= 0){
            pajakSetahun = penghasilanNetto*0.05;
        }else if ((penghasilanNetto - 50) <= 250 && (penghasilanNetto - 50) >=50){
            pajakLayer1 = 2.5;
            pajakLayer2 = (penghasilanNetto - 50)*0.1;
            pajakSetahun = pajakLayer1 + pajakLayer2;
        }else {
            pajakLayer1 = 2.5;
            pajakLayer2 = 25.0;
            pajakLayer3 = (penghasilanNetto - 250)*0.15;
            pajakSetahun = pajakLayer1 + pajakLayer2 + pajakLayer3;
        }
        System.out.println("pajakLayer1 = "+ pajakLayer1);
        System.out.println("pajakLayer2 = "+ pajakLayer2);
        System.out.println("pajakLayer3 = "+pajakLayer3);
        System.out.println("pajakSetahun = "+ pajakSetahun);

        Double pajakPerbulan = pajakSetahun/12;
        System.out.println("pajakPerbulan = "+pajakPerbulan);
        return pajakPerbulan;
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
