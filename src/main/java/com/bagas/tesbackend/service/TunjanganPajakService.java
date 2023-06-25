package com.bagas.tesbackend.service;

import com.bagas.tesbackend.model.Employee;
import com.bagas.tesbackend.model.HitungPajakRequest;
import com.bagas.tesbackend.model.HitungPajakResponse;
import com.bagas.tesbackend.model.Komponen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TunjanganPajakService {

    @Autowired
    HitungPajakService hitungPajakService;

    public HitungPajakResponse tunjanganPajak(HitungPajakRequest request){

        double penghasilanTahunan = 285.0 * 1000000; // 285 juta
        double pajakTerbayar = 2166000; // 2.166.000 rupiah
        double tunjanganPajak = 2167000; // Mulai dengan nilai yang lebih besar dari pajakTerbayar
        double penghasilanTotal = penghasilanTahunan + (12 * tunjanganPajak);
        double pajak = hitungPajak(penghasilanTotal); // Method hitungPajak menghitung jumlah pajak berdasarkan penghasilan

        double perbedaan = pajak - pajakTerbayar;

        Integer tunjangan = 0;

        while (perbedaan > 1000 || perbedaan < -1000) {
            if (perbedaan < 0) {
                tunjanganPajak += 1000;
            } else {
                tunjanganPajak -= 1000;
            }

            penghasilanTotal = penghasilanTahunan + (12 * tunjanganPajak);
            pajak = hitungPajak(penghasilanTotal);
            perbedaan = pajak - pajakTerbayar;

            tunjangan = (int) tunjanganPajak;
            System.out.println(tunjangan);


        }

        System.out.println("Jumlah tunjangan pajak yang menghasilkan jumlah pajak yang sama dengan pajak terbayar: " + tunjangan);

        HitungPajakResponse hitungPajakResponse = new HitungPajakResponse();

        return hitungPajakResponse;
    }

    public Double hitungPajak (Double penghasilanBersih){
        Double pajakBaru=0.0;
        if (penghasilanBersih <= 50_000_000) {
                pajakBaru = 0.05 * penghasilanBersih;
            } else if (penghasilanBersih > 50_000_000 && penghasilanBersih <= 250_000_000) {
                pajakBaru = 0.10 * (penghasilanBersih - 50_000_000) + (0.05 * 50_000_000);
            } else {
                pajakBaru = 0.15 * (penghasilanBersih - 250_000_000) + (0.10 * 200_000_000) + (0.05 * 50_000_000);
            }

        return pajakBaru;
    }
}
