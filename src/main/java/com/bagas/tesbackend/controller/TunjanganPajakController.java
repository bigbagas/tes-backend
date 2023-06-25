package com.bagas.tesbackend.controller;

import com.bagas.tesbackend.model.HitungPajakRequest;
import com.bagas.tesbackend.model.HitungPajakResponse;
import com.bagas.tesbackend.service.TunjanganPajakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TunjanganPajakController {

    @Autowired
    private TunjanganPajakService tunjanganPajakService;

    //Test Backend PT CIN
    // Soal Tunjangan Pajak (Middle)

    @PostMapping(
            path = "/tunjanganpajak",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public HitungPajakResponse tunjanganPajak (@RequestBody HitungPajakRequest request){
        HitungPajakResponse response = tunjanganPajakService.tunjanganPajak(request);

        return response;

    }
}
