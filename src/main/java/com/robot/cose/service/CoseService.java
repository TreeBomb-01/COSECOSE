package com.robot.cose.service;

import com.robot.cose.dto.CoseDTO;
import com.robot.cose.dto.CoseRequestDTO;
import com.robot.cose.mapper.CoseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoseService {
    private final CoseMapper coseMapper;
    public void create_cose(String uuid, CoseRequestDTO dto){
        coseMapper.createCose(uuid,dto); //코스 생성
        coseMapper.createCosePlaces(dto);
    }

    public List<Map<String, Object>> get_cose(String uuid){
        return coseMapper.getCose(uuid);
    }

    public List<Map<String, Object>> transformData(List<Map<String, Object>> rawData) {
        // 데이터를 그룹화
        return rawData.stream()
                .collect(Collectors.groupingBy(
                        data -> Map.of(
                                "coseIdx", data.get("coseIdx"),
                                "coseName", data.get("coseName")
                        ),
                        LinkedHashMap::new,
                        Collectors.mapping(data -> Map.of(
                                "dateSpotIdx", data.get("dateSpotIdx"),
                                "spotName", data.get("spotName"),
                                "imageURL", data.get("imageURL"),
                                "locate", data.get("locate"),
                                "order_in_cose", data.get("order_in_cose")
                        ), Collectors.toList())
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>(entry.getKey());
                    result.put("places", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

}
