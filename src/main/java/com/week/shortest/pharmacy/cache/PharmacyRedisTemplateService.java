package com.week.shortest.pharmacy.cache;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.week.shortest.pharmacy.dto.PharmacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRedisTemplateService {

    private static final String CACHE_KEY = "PHARMACY";

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;


    //캐시키, 서브키(약국 데이터의 pk), 밸류(om을 활용하여 약국 dto를 스트링으로)
    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }


    public void save(PharmacyDto pharmacyDto){
        if(Objects.isNull(pharmacyDto)||Objects.isNull(pharmacyDto.getId())){
            log.error("[PharmacyRedisTemplateService save]  parmacyDto 칼럼에 null { }", pharmacyDto);
            return;
        }

        try{
            hashOperations.put(CACHE_KEY, pharmacyDto.getId().toString(), serializePharmacyDto(pharmacyDto));
        }catch(Exception e){
            log.error("[PharmacyRedisTemplateService save]  { }",e.getMessage());
        }
    }

    public List<PharmacyDto> findAll(){

        try{
            List<PharmacyDto> list = new ArrayList<>();
            for(String value : hashOperations.entries(CACHE_KEY).values()){
                PharmacyDto pharmacyDto = desrializePharmacyDto(value);
                list.add(pharmacyDto);
            }
            return list;
        }catch(Exception e){
            log.error("[PharmacyRedisTemplateService findAll] {}", e.getMessage());
            return Collections.emptyList();
        }

    }

    public void delete(Long id){
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[PharmacyRedisTemplateService delete] pharmacyId : {}", id);
    }



    private String serializePharmacyDto(PharmacyDto pharmacyDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pharmacyDto);
    }

    private PharmacyDto desrializePharmacyDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, PharmacyDto.class);
    }



}
