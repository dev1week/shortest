package com.week.shortest.pharmacy;

import com.week.shortest.pharmacy.cache.PharmacyRedisTemplateService;
import com.week.shortest.pharmacy.dto.PharmacyDto;
import com.week.shortest.pharmacy.entity.Pharmacy;
import com.week.shortest.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    @GetMapping("/redis/save")
    public String save(){

        List<Pharmacy> pharmacies = pharmacyRepositoryService.findAll();


        for(Pharmacy p : pharmacies){
            pharmacyRedisTemplateService.save(PharmacyDto.builder().id(p.getId()).pharmacyName(p.getPharmacyName()).pharmacyAddress(p.getPharmacyAddress()).latitude(p.getLatitude()).longitude(p.getLongitude()).build());
        }
        return "success";
    }


}
