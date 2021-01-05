package com.stockcalendar.formpage.controller;


import com.stockcalendar.formpage.dto.StockDto;
import com.stockcalendar.formpage.service.StockDtoService;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.text.ParseException;

/*
MAIN MVC CONTROLLER
 */

@Controller
public class StockController {

    /*
    Show Generate Form
    Takes in 3 parameters (Ticker | StartDate | EndDate)
     */
    @GetMapping("/generate")
    public String showGenerateForm(Model model){
        model.addAttribute("StockDto", new StockDto());
        return "generate_form";
    }

    /*
    Computes Backend
    Generates a new StockDto Obj
    Adds objects to HTML & JS
     */
    @PostMapping("/results")
    public String showResults(@ModelAttribute("StockDto") StockDto stockDto, Model model) throws JSONException, InterruptedException, ParseException, IOException {
        StockDto stockDtoResult = StockDtoService.generate(stockDto);

        //Check if there was no data found
        if(stockDtoResult == null) return "error_form";

        model.addAttribute("StockDtoResult", stockDtoResult);
        model.addAttribute("Ticker", stockDtoResult.getTicker());
        model.addAttribute("StartDate", stockDtoResult.getStartDate());
        model.addAttribute("EndDate", stockDtoResult.getEndDate());
        for(int i = 0; i<stockDtoResult.getPercentChange().size(); ++i){
            model.addAttribute("PercentChange" + Integer.toString(i), stockDtoResult.getPercentChange().get(i).getNum());
            model.addAttribute("PriceChange" + Integer.toString(i), stockDtoResult.getPriceChange().get(i).getNum());
            model.addAttribute("YearVal" + Integer.toString(i), stockDtoResult.getPriceChange().get(i).getDate());
        }
        model.addAttribute("AveragePercentChange", stockDtoResult.getAveragePercentChange());
        return "result_form";

    }


}
