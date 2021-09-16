package com.deyuan.controller;

import com.deyuan.entity.Page;
import com.deyuan.service.EsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/search")
public class EsSearchController {
    @Autowired
    private EsSearchService esSearchService;

    @RequestMapping("/list")
    public String searchMap(@RequestParam Map<String, String> searchMap, Model model) {
        Map result = esSearchService.search(searchMap);
        model.addAttribute("result", result);
        model.addAttribute("searchMap", searchMap);
        long total = Long.valueOf(String.valueOf(result.get("total")));
        int pageNum = Integer.valueOf(String.valueOf(result.get("pageNum")));

        Page page = new Page(total, pageNum, 30);
        model.addAttribute("page", page);
        return "search";
    }
}
