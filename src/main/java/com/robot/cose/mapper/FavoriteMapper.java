package com.robot.cose.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FavoriteMapper {

    void insertFavorites(@Param("memberUuid") String memberUuid, @Param("favorites") List<Map<String, String>> favorites);
}
