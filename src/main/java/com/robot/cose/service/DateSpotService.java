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
        // 카테고리별로 최대 5개씩 조회
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

        // 카테고리별 최대 5개 데이터만 유지
        dateSpotsByCategory.forEach((category, spots) -> {
            dateSpotsByCategory.put(category, spots.stream().limit(5).toList());
        });

        return dateSpotsByCategory;
    }
}
