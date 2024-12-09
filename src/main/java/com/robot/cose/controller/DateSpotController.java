package com.robot.cose.controller;

import com.robot.cose.dto.DateSpotDTO;
import com.robot.cose.service.DateSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DateSpotController {
    public final DateSpotService dateSpotService;

    @GetMapping("/get_main_datespot")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getDateSpotsByCategory() {
        return ResponseEntity.ok(dateSpotService.getDateSpotsMainCategory());
    }

    @GetMapping("/get_datespot")
    public ResponseEntity<List<DateSpotDTO>> getDateSpot() {
        DateSpotDTO dto = new DateSpotDTO();
        List<DateSpotDTO> list = dateSpotService.getDateSpot();
        //List<DateSpotDTO> response = new HashMap<>();

/*
        response.put("id",Integer.toString(dto.getDateSpotIdx()));
        response.put("name",dto.getSpotName());
        response.put("locate",dto.getLocate());
        response.put("info",dto.getInfo());
        response.put("imageURL",dto.getImageURL());
*/

/*        for(int i =0;i<list.size();i++){
            DateSpotDTO d = list.get(i);
            response.put(i,d);
        }*/
        System.out.println(list);

        return ResponseEntity.ok(list);
    }
}
