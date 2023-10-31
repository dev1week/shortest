package com.week.shortest.pharmacy.service;

import com.week.shortest.pharmacy.cache.PharmacyRedisTemplateService;
import com.week.shortest.pharmacy.dto.PharmacyDto;
import com.week.shortest.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {


    private final PharmacyRepositoryService pharmacyRepositoryService;

    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;


    public List<PharmacyDto> searchPharmacyDtoList(){
        //redis로 먼저 시도
        List<PharmacyDto> list=  pharmacyRedisTemplateService.findAll();
        if(!list.isEmpty())return list;


        //db 관련 로직

        List<Pharmacy> searchResult = pharmacyRepositoryService.findAll();
        List<PharmacyDto> convertedResult = new ArrayList<>();
        for(Pharmacy pharmacy : searchResult){
                convertedResult.add(convertToDto(pharmacy));
        }

        return convertedResult;
    }

    private PharmacyDto convertToDto(Pharmacy pharmacy){
        return PharmacyDto.builder()
                            .id(pharmacy.getId())
                            .pharmacyAddress(pharmacy.getPharmacyAddress())
                            .pharmacyName(pharmacy.getPharmacyName())
                            .latitude(pharmacy.getLatitude())
                            .longitude(pharmacy.getLongitude())
                            .build();





    }

}
