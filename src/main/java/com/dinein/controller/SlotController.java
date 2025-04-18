package com.dinein.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dinein.model.Slot;
import com.dinein.repository.SlotRepository;

@RequestMapping("/slot")
@Controller
public class SlotController {

	@Autowired
	SlotRepository repo;

	
	@RequestMapping("/list")
    public String home(Model model) {
        model.addAttribute("datalist", repo.findAll());
        return "slot";
    }
	
	
	@RequestMapping("/save")
	public String save(Slot obj) throws IOException, ParseException{
		
		SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");  
        Date date = sdf24.parse(obj.getSlotTime());
        
        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");  
        obj.setSlotTime(sdf12.format(date));
		Optional<Slot> slot = repo.findBySlotTime(obj.getSlotTime());
		
		if(slot.isEmpty())
			repo.save(obj);	
		
		return "redirect:/slot/list";
	}
	
	
	 @RequestMapping("/delete/{id}")
	    public String delete(@PathVariable String id) {
	        Optional<Slot> obj = repo.findById(id);
	        repo.delete(obj.get());
	        return "redirect:/slot/list";
	    }
	    
	   
}
