package com.adrianstypinski.config.controller;

import com.adrianstypinski.datamodel.Data;
import com.adrianstypinski.service.DataService;
import com.adrianstypinski.util.Mappings;
import com.adrianstypinski.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
public class StatsController {
    private final DataService dataService;

    @ModelAttribute
    public Data data() {
        return dataService.getData();
    }

    @Autowired
    public StatsController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping(Mappings.STATS)
    public String stats(Model model) {
        model.addAttribute("titles", dataService.getData().getTitles());
        return ViewNames.STATS;
    }
}
