package com.week.shortest.api.service;

import com.week.shortest.api.dto.KakaoApiResponseDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoCategorySearchService {

    private final KakaoUriBuilderService kakaoUriBuilderService;


    private final RestTemplate restTemplate;

    @Value("{KAKAO_REST_API_KEY}")
    private String kakaoRestApiKey;

    public KakaoApiResponseDto requestPharmacyCategorySearch(double latitude, double longitude, double radius){

        URI uri = kakaoUriBuilderService.buildUriByCategorySearch(latitude, longitude, radius, "PM9");

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAk "+ kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();
    }

}
