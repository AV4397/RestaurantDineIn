package com.dinein.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping; // for @GetMapping
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;           // JSON exception
import com.fasterxml.jackson.databind.ObjectMapper;                  // JSON mapper

import java.util.List;

import com.dinein.model.Menu;
import com.dinein.repository.CategoryRepository;
import com.dinein.repository.MenuRepository;
import com.dinein.service.FileUploadUtil;

@RequestMapping("/menu")
@Controller
public class MenuController {

	@Autowired
	MenuRepository repo;

	@Autowired
	private MenuRepository menuRepo;
	
	@Autowired
	CategoryRepository catRepo;
	
	@RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("datalist", repo.findAll());
        return "menu";
    }
	
	@RequestMapping("/create")
	public String create(Model model) {
		model.addAttribute("categories", catRepo.findAll());
		return "menu_create";
	}
	
	@RequestMapping("/save")
	public String save(Menu obj, @RequestParam("image") MultipartFile multipartFile) throws IOException{
		Optional<Menu> idobj = repo.findTopByOrderByIdDesc();
		String id = null;
		if(idobj.isPresent())
		{
			int idnum = Integer.parseInt(idobj.get().getMenuId().substring(5));
			idnum++;
			id = "MENU3"+idnum;
		}
		else
		{
			id = "MENU362353";
		}
		
		String imgUrl = id+multipartFile.getOriginalFilename();
		obj.setMenuId(id);
		obj.setImgUrl(imgUrl);
		
		repo.save(obj);		
		
		String uploadDir = "uploads";
        
        FileUploadUtil.saveFile(uploadDir, imgUrl, multipartFile);
		return "redirect:/menu/list";
	}
	

	
	@RequestMapping("/show/{id}")
	public String show(@PathVariable String id, Model model) {
		model.addAttribute("obj",repo.findById(id).get());
		model.addAttribute("catName",catRepo.findByCatId(repo.findById(id).get().getCatId()).get().getName());
		return "menu_show";
	}
	
	 @RequestMapping("/delete")
	    public String delete(@RequestParam String id) {
	        Optional<Menu> obj = repo.findById(id);

	        repo.delete(obj.get());
	        return "redirect:/menu/list";
	    }
	    
	    @RequestMapping("/edit/{id}")
	    public String edit(@PathVariable String id, Model model) {
	        model.addAttribute("obj", repo.findById(id).get());
			model.addAttribute("categories", catRepo.findAll());

	        return "menu_edit";
	    }
	    
	    @RequestMapping("/update")
	    public String update(Menu obj) {
		    repo.save(obj);

	        return "redirect:/menu/show/" + obj.getId();
	    }

		@GetMapping("/menu")
		public String menuPage(Model model) throws JsonProcessingException {
			Iterable<Menu> all = menuRepo.findAll();
			String json = new ObjectMapper().writeValueAsString(all);
			model.addAttribute("menuDataJson", json);
			return "cust_menu";
		}

}
