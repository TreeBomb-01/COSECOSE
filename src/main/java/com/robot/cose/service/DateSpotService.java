package com.robot.cose.service;

import com.robot.cose.dto.DateSpotDTO;
import com.robot.cose.mapper.DateSpotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DateSpotService {
    final DateSpotMapper dateSpotMapper;

    public List<DateSpotDTO> getDateSpot() {
        return dateSpotMapper.getAllDateSpotById();
    }

    public Map<String, List<Map<String, Object>>> getDateSpotsMainCategory() {
        List<Map<String, Object>> results = dateSpotMapper.findMainCategory();
        Map<String, List<Map<String, Object>>> dateSpotsByCategory = new HashMap<>();

        for (Map<String, Object> result : results) {
            String categoryName = (String) result.get("categoryName");

            String tagsString = (String) result.get("tags");
            List<String> tags = tagsString != null ? List.of(tagsString.split(", ")) : new ArrayList<>();

            // 태그 리스트를 추가
            result.put("tags", tags);

            dateSpotsByCategory.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(result);
        }

        // 카테고리별 최대 5개 데이터만 유지 (수정 필요)
        dateSpotsByCategory.forEach((category, spots) -> {
            dateSpotsByCategory.put(category, spots.stream().limit(5).toList());
        });

        return dateSpotsByCategory;
    }

    public List<Map<String, Object>> getDateSpots() {
        List<Map<String, Object>> results = dateSpotMapper.findMainCategory();

        // 기존 리스트를 반복하면서 tags를 리스트로 변환
        for (Map<String, Object> result : results) {
            // 기존 tags 문자열을 가져오기
            String tagsString = (String) result.get("tags");

            // 문자열을 List<String>으로 변환
            List<String> tags = tagsString != null ? List.of(tagsString.split(", ")) : new ArrayList<>();

            // 변환된 tags 리스트를 기존 Map에 덮어쓰기
            result.put("tags", tags);
        }

        // 변환된 리스트를 반환
        return results;
    }

    public Map<String, Object> getDateSpotbyId(int id) {
        Map<String, Object> result = dateSpotMapper.getDateSpotById(id);
        return result;
    }
}
