package com.bagas.tesbackend.controller;

import com.bagas.tesbackend.model.HitungPajakRequest;
import com.bagas.tesbackend.model.HitungPajakResponse;
import com.bagas.tesbackend.service.HitungPajakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HitungPajakController {

    @Autowired
    private HitungPajakService hitungPajakService;

    //Test Backend PT CIN
    // Soal Penggajian (Junior)

    @PostMapping(
            path = "/hitungpajak",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public HitungPajakResponse hitungPajak (@RequestBody HitungPajakRequest request){
        HitungPajakResponse response = hitungPajakService.hitungPajak(request);

        return response;

    }

}
