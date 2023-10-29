package com.week.shortest.pharmacy.service;

import com.week.shortest.api.dto.DocumentDto;
import com.week.shortest.api.dto.KakaoApiResponseDto;
import com.week.shortest.api.service.KakaoAddressSearchService;
import com.week.shortest.direction.dto.OutputDto;
import com.week.shortest.direction.entity.Direction;
import com.week.shortest.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {
    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public List<OutputDto> recommendPharmacyList(String address){
        log.info("recommend {}", address);
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto)|| CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())){
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input Address {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        List<Direction> directions = directionService.buildDirectionList(documentDto);
//        List<Direction> directions = directionService.buildDirectionListByCategorySearch(documentDto);
        log.info("address: { }",address);
        log.info("directions: { }",directions);
        directionService.saveAll(directions);

        List<OutputDto> outputDtos = new ArrayList<>();
        for(Direction direction : directions){
            OutputDto outputDto = convertToOutputDto(direction);
            outputDtos.add(outputDto);
        }
        log.debug("directions: { }",outputDtos);
        return outputDtos;
    }


    private OutputDto convertToOutputDto(Direction direction){
        String params = String.join(",", direction.getTargetPharmacyName(),
                String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));

        String result = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params)
                .toUriString();
        log.info("direction params: {}, url: {}" ,params ,result);

        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl("result")
                .roadViewUrl("ROAD_VIEW_BASE_URL" + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }






}
