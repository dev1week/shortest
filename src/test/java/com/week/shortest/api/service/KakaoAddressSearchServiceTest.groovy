package com.week.shortest.api.service

import com.week.shortest.AbstractionIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoAddressSearchServiceTest extends AbstractionIntegrationContainerBaseTest {


    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 null이면, requestAddressSearch 메서드는 null을 리턴한다."(){
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null

    }

    def "주소값이 유효하다면, requestAddressSearch 메서드는 정상적으로 실행된다."(){
        given:
        def address = "서울 성북구 종암로 18길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() >0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null



    }

}
