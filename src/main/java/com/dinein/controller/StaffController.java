package com.dinein.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping; // for @GetMapping
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;           // JSON exception
import com.fasterxml.jackson.databind.ObjectMapper;                  // JSON mapper

import java.util.List;

import com.dinein.model.Login;
import com.dinein.model.Staff;
import com.dinein.repository.StaffRepository;
import com.dinein.service.Encryption;

@RequestMapping("/staff")
@Controller
public class StaffController {

	
	@Autowired
	StaffRepository repo;

	@Autowired
	private StaffRepository staffRepo;
	
	@RequestMapping()
    public String login(Model model) {
        return "staff_login";
    }	
	
	
	@RequestMapping("/home")
    public String home(Model model, HttpServletRequest req) {
		return "staff_home";
    }
	
	@RequestMapping("/logout")
    public String logout(Model model, HttpServletRequest req) {
        req.getSession().invalidate();
        return "logout";
    }
	
	@PostMapping("/login")
	public String show(Login login, Model model, HttpServletRequest request) throws Exception {
		if(login.getEmail().equals("admin") && login.getPassword().equals("admin"))
		{
			request.getSession().setAttribute("id", "1001");
			request.getSession().setAttribute("userid", "STAFF00001");
			request.getSession().setAttribute("usertype", "Admin");
			request.getSession().setAttribute("name", "Admin");
			return "staff_home";
			
		}
		Optional<Staff> obj = repo.findByEmailIdAndPassword(login.getEmail(),Encryption.encrypt(login.getPassword()));
		if(obj.isPresent())
		{
			if((Encryption.decrypt(obj.get().getPassword())).equals("staff")) {
				model.addAttribute("obj",obj.get());
				return "password_reset";
			}
			
				
			model.addAttribute("name",obj.get().getFemployeeName());
			request.getSession().setAttribute("id", obj.get().getId());
			request.getSession().setAttribute("userid", obj.get().getEmployeeId());
			request.getSession().setAttribute("usertype", obj.get().getDesignation());
			request.getSession().setAttribute("name", obj.get().getFemployeeName());
			return "staff_home";
		}
		else
		{
			model.addAttribute("msg","Invalid Login Credentials");
			return "staff_login";
		}
	}
	
	@PostMapping("/password/reset")
	public String passwordReset(Login login, Model model, HttpServletRequest request) throws Exception {

		Optional<Staff> obj = repo.findByEmailIdAndPassword(login.getEmail(),Encryption.encrypt("staff"));
		
		if(obj.isPresent())
		{
			System.out.println(obj.get().getPassword());
			System.out.println(Encryption.decrypt(obj.get().getPassword()));
			System.out.println(login.getPassword());
			System.out.println(Encryption.encrypt(login.getPassword()));
			
			Staff staff = obj.get();
			staff.setPassword(Encryption.encrypt(login.getPassword()));
			repo.save(staff);	
			
			model.addAttribute("msg","Reset Password  Successful");
			return "staff_login";
		}
		else
		{
			model.addAttribute("msg","Invalid Login Credentials");
			return "staff_login";
		}
	}
	
	
	
	@RequestMapping("/list")
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("datalist", repo.findAll());
        return "staff";
    }
	
	@RequestMapping("/create")
	public String create(Model model, HttpServletRequest request) {
		return "staff_create";
	}
	
	@RequestMapping("/save")
	public String save(Staff obj, Model model) throws Exception{
		
		if(repo.findByEmailId(obj.getEmailId()).isPresent()) {
			model.addAttribute("msg","This Email Id already registered");
			return "staff_login";
		}
		
		Optional<Staff> idobj = repo.findTopByOrderByIdDesc();
		String id = null;
		if(idobj.isPresent())
		{
			int idnum = Integer.parseInt(idobj.get().getEmployeeId().substring(5));
			idnum++;
			id = "STAF0"+idnum;
		}
		else
		{
			id = "STAF021301";
		}
		obj.setEmployeeId(id);
		
		obj.setPassword(Encryption.encrypt(obj.getPassword()));
		
		
		repo.save(obj);		
		return "redirect:/staff/list";
	}
	
	@RequestMapping("/show/{id}")
	public String show(@PathVariable String id, Model model, HttpServletRequest request) {
		model.addAttribute("obj",repo.findById(id).get());
		return "staff_show";
	}
	
	 @RequestMapping("/delete")
	    public String delete(@RequestParam String id) {
	        Optional<Staff> obj = repo.findById(id);
	        repo.delete(obj.get());

	        return "redirect:/staff/list";
	    }
	    
	    @RequestMapping("/edit/{id}")
	    public String edit(@PathVariable String id, Model model) {
	        model.addAttribute("obj", repo.findById(id).get());
	        return "staff_edit";
	    }
	    
	    @RequestMapping("/edit")
	    public String edit(Model model, HttpServletRequest req) throws Exception {
	    	
	    	Staff staff = repo.findByEmployeeId(req.getSession().getAttribute("userid").toString()).get();
	    	staff.setPassword(Encryption.decrypt(staff.getPassword()));
	        model.addAttribute("obj", staff);
	        return "staff_edit";
	    }
	    
	    @RequestMapping("/update")
	    public String update(Staff obj, HttpServletRequest req) throws Exception {
	    	obj.setPassword(Encryption.encrypt(obj.getPassword()));
	        repo.save(obj);
	        if(req.getSession().getAttribute("usertype").equals("Admin"))
	        	return "redirect:/staff/show/" + obj.getId();
	        else
	        	return "staff_home";
	    }

		@GetMapping("/staff")
		public String staffPage(Model model) throws JsonProcessingException {
			Iterable<Staff> all = staffRepo.findAll();
			String json = new ObjectMapper().writeValueAsString(all);
			model.addAttribute("staffDataJson", json);
			return "staff";
		}
	
}
