package com.week.shortest.pharmacy.repository

import com.week.shortest.AbstractionIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.week.shortest.pharmacy.entity.Pharmacy
import spock.lang.Specification



class PharmacyRepositoryTest extends  AbstractionIntegrationContainerBaseTest{

    @Autowired
    private PharmacyRepository pharmacyRepository

    def "Pharmacy Repository save"(){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
            .pharmacyAddress(address)
            .pharmacyName(name)
            .latitude(latitude)
            .longitude(longitude)

        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getLatitude() == latitude
        result.getLongitude() == longitude



    }


}
