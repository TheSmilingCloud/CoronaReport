package com.org.CoronaReport;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

	@Autowired
	CoronaReportService coronaReportService;
	
	@Autowired
	PagingService pagingService;
	
	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}
	
	@GetMapping("/coronaReport")
	public String coronaReport(Model model) {
		int openPageNum = 1;
		int openPageSize = 10;
		String sortField = "id";
		String sortDirection = "asc";
		return findPages(openPageNum, openPageSize, sortField, sortDirection, model);
	}
	
	@GetMapping("/page/{pageNo}/{pageSize}")
	public String findPages(@PathVariable (value = "pageNo") int pageNo, 
			@PathVariable (value = "pageSize") int pageSize,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDirection") String sortDirection,
			Model model) {
		
		
		Page<CovidData> page = pagingService.findPaginated(pageNo, pageSize, sortField, sortDirection);
		List<CovidData> list = page.getContent();
		int pageLinks = 20;
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("currentPageSize", pageSize);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("pageLinks", pageLinks);
		model.addAttribute("sortField", sortField);
		model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
		model.addAttribute("sortDirection", sortDirection);

			
		model.addAttribute("list", list);
		return "coronaReport";
	}
	
	@GetMapping("storeToH2Database")
	public String storeToH2Database() throws IOException, InterruptedException, ParseException {
		coronaReportService.fetchCovidData();
		coronaReportService.storeToH2Database();
		return "home";
	}

}
