package com.week.shortest.pharmacy.service;

import com.week.shortest.api.dto.DocumentDto;
import com.week.shortest.api.dto.KakaoApiResponseDto;
import com.week.shortest.api.service.KakaoAddressSearchService;
import com.week.shortest.direction.entity.Direction;
import com.week.shortest.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacyList(String address){

        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto)|| CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())){
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input Address {}", address);
            return;
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        List<Direction> directions = directionService.buildDirectionList(documentDto);

        directionService.saveAll(directions);

    }






}
