package com.week.shortest.direction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="direction")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Direction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    //고객의 위치 정보
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    //약국의 위치 정보
    private String targetPharmacyName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;


    //고객과 약국 사이 거리
    private double distance;


}
