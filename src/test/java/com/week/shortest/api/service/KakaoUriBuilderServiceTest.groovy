package com.week.shortest.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoUriBuilderServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService

    def setup(){
        kakaoUriBuilderService = new KakaoUriBuilderService();
    }

    def "buidUriByAddressSearch - 한글 파라미터 인코딩 정상작동 확인"(){
        given:
        String address = "서울 성복구"
        def charset = StandardCharsets.UTF_8

        when:
        def uri= kakaoUriBuilderService.buildUriByAddressSearch(address);
        def decodedResult= URLDecoder.decode(uri.toString(), charset);

        then:
        decodedResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 성복구";
    }
}
