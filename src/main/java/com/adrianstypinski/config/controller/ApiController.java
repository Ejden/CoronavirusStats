package com.adrianstypinski.config.controller;

import com.adrianstypinski.service.DataService;
import com.adrianstypinski.util.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {
    private final DataService dataService;

    @Autowired
    public ApiController(DataService dataService) {
        this.dataService = dataService;
    }

    //JSON
    @ResponseBody
    @GetMapping(Mappings.POINTS)
    public String points(@RequestParam(required = false, defaultValue = "false") boolean onlyWithActiveCases) {
        return dataService.getPoints(onlyWithActiveCases);
    }

    @ResponseBody
    @GetMapping(Mappings.LOCATIONS)
    public String locations() {
        return dataService.getLocations();
    }

    @ResponseBody
    @GetMapping(Mappings.CASES_HISTORY)
    public String casesHistory(@RequestParam int id) {
        return dataService.getCasesHistory(id);
    }
}
