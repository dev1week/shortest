package com.week.shortest.direction.service;

import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Base62Service {

    private static final Base62 base62 = Base62.createInstance();

    public String encodeDirectionId(Long directionId){
        return new String(base62.encode(String.valueOf(directionId).getBytes()));
    }

    public Long decodeDirectionId(String encodedDirectionId){
        String decodedDirectionId = new String(base62.decode(encodedDirectionId.getBytes()));
        return Long.valueOf(decodedDirectionId);
    }
}
