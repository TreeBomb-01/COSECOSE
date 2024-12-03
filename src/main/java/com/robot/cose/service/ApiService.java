package com.robot.cose.service;

import com.robot.cose.mapper.FavoriteMapper;
import com.robot.cose.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiService {
    private MemberMapper memberMapper;

    public void saveFavorites(String memberUuid, Map<String, List<String>> favoriteMap) {
        List<Map<String, String>> favorites = new ArrayList<>();

        // favoriteMap을 각 카테고리별로 처리
        favoriteMap.forEach((category, values) -> {
            for (String value : values) {
                Map<String, String> favorite = new HashMap<>();
                favorite.put("category", category);
                favorite.put("value", value);
                favorites.add(favorite);
            }
        });

        // Mapper 호출
        memberMapper.insertFavorites(memberUuid, favorites);
    }

}
