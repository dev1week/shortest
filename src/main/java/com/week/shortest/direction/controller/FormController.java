package com.week.shortest.direction.controller;


import com.week.shortest.direction.dto.InputDto;
import com.week.shortest.direction.dto.OutputDto;
import com.week.shortest.pharmacy.service.PharmacyRecommendationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FormController {

    private final PharmacyRecommendationService pharmacyRecommendationService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @PostMapping("search")
    public String postDirection(@ModelAttribute InputDto inputDto, Model model){
        log.info("inputDto : {}",inputDto);
        List<OutputDto> outputDtos = pharmacyRecommendationService.recommendPharmacyList(inputDto.getAddress());

        model.addAttribute("outputFormList", outputDtos);

        return "output";
    }


}
