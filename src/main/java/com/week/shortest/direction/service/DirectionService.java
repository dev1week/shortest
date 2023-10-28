package com.week.shortest.direction.service;

import com.week.shortest.direction.entity.Direction;
import com.week.shortest.api.dto.DocumentDto;
import com.week.shortest.pharmacy.dto.PharmacyDto;
import com.week.shortest.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_LIMIT = 10;

    private final PharmacySearchService pharmacySearchService;



    public List<Direction> buildDirectionList(DocumentDto documentDto){

        if(Objects.isNull(documentDto)) return Collections.emptyList();

        List<PharmacyDto> searchResult = pharmacySearchService.searchPharmacyDtoList();

        List<Direction> result = new ArrayList<>();


        for(PharmacyDto pharmacy : searchResult){
            double distance = calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), pharmacy.getLatitude(),pharmacy.getLongitude() );
            if(distance>RADIUS_LIMIT) continue;
            Direction direction =  Direction.builder().
                    targetAddress(pharmacy.getPharmacyAddress()).
                    targetPharmacyName(pharmacy.getPharmacyName()).
                    targetLatitude(pharmacy.getLatitude()).
                    targetLongitude(pharmacy.getLongitude()).
                    inputAddress(documentDto.getAddressName()).
                    inputLatitude(documentDto.getLatitude()).
                    inputLongitude(documentDto.getLongitude()).
                    distance(distance).
                    build();
            result.add(direction);
        }


        Collections.sort(result, Comparator.comparing(Direction::getDistance));

        if(result.size()<=MAX_SEARCH_COUNT){
            return result;
        }else{
            List<Direction> topNResult = new ArrayList<>();
            for(int i=0; i<MAX_SEARCH_COUNT; i++) {
                topNResult.add(result.get(i));
            }
            return topNResult;
        }
    }



    private double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);


        double earthRadius = 6371;

        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

    }

}
