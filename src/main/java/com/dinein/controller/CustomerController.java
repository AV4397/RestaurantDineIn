package com.dinein.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

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

import com.dinein.model.Customer;
import com.dinein.model.Login;
import com.dinein.model.Table;
import com.dinein.repository.CustomerRepository;
import com.dinein.repository.MenuRepository;
import com.dinein.repository.SlotRepository;
import com.dinein.repository.TableRepository;
import com.dinein.service.Encryption;

@RequestMapping("/customer")
@Controller
public class CustomerController {

	@Autowired
	CustomerRepository repo;

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	TableRepository tableRepo;	
	
	@Autowired
	SlotRepository slotRepo;

	@Autowired
	MenuRepository menuRepo;
	
	@RequestMapping()
    public String login(Model model) {
        return "cust_login";
    }	
	
	
	@RequestMapping("index")
    public String index(Model model) {
        return "index";
    }		

	@RequestMapping("menu")
    public String menu(Model model) {
		model.addAttribute("datalist", menuRepo.findAll());
        return "cust_menu";
    }	


	@RequestMapping("booking")
    public String booking(Model model) {

		List<Table> tables = tableRepo.findDistinctByCapacity();
		Set<Integer> capacitylist = new TreeSet<Integer>();
		for(Table t:tables)
		{
			capacitylist.add(t.getCapacity());
		}
		model.addAttribute("tablelist", capacitylist);
		model.addAttribute("slotlist", slotRepo.findAll());
        return "cust_booking";
    }	
	
	
	@RequestMapping("/home")
    public String home(Model model, HttpServletRequest req) {
		return "cust_home";
    }
	
	@RequestMapping("/logout")
    public String logout(Model model, HttpServletRequest req) {
        req.getSession().invalidate();
        return "logout";
    }
	
	@PostMapping("/login")
	public String show(Login login, Model model, HttpServletRequest request) throws Exception {
		Optional<Customer> obj = repo.findByEmailIdAndPassword(login.getEmail(),Encryption.encrypt(login.getPassword()));
		if(obj.isPresent())
		{
			model.addAttribute("name",obj.get().getFname());
			request.getSession().setAttribute("id", obj.get().getId());
			request.getSession().setAttribute("userid", obj.get().getCustomerId());
			request.getSession().setAttribute("usertype", "customer");
			request.getSession().setAttribute("name", obj.get().getFname());
			return "redirect:/customer/home";
		}
		else
		{
			model.addAttribute("msg","Invalid Login Credentials");
			return "cust_login";
		}
	}
	
	@RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("datalist", repo.findAll());
        return "cust";
    }
	
	@RequestMapping("/create")
	public String create(Model model) {
		return "cust_create";
	}
	
	@RequestMapping("/save")
	public String save( Customer obj) throws Exception{
		Optional<Customer> idobj = repo.findTopByOrderByIdDesc();
		String id = null;
		if(idobj.isPresent())
		{
			int idnum = Integer.parseInt(idobj.get().getCustomerId().substring(5));
			idnum++;
			id = "CUST0"+idnum;
		}
		else
		{
			id = "CUST064901";
		}
		
		obj.setCustomerId(id);
		obj.setPassword(Encryption.encrypt(obj.getPassword()));
		repo.save(obj);		
		return "redirect:/customer";
	}
	
	@RequestMapping("/show/{id}")
	public String show(@PathVariable String id, Model model) {
		model.addAttribute("obj",repo.findById(id).get());
		return "cust_show";
	}
	
	 @RequestMapping("/delete")
	    public String delete(@RequestParam String id) {
	        Optional<Customer> obj = repo.findById(id);
	        repo.delete(obj.get());

	        return "redirect:/customer/list";
	    }
	    
	    @RequestMapping("/edit")
	    public String edit( Model model, HttpServletRequest req) throws Exception {
	    	Customer cust = repo.findByCustomerId(req.getSession().getAttribute("userid").toString()).get();
	    	cust.setPassword(Encryption.decrypt(cust.getPassword()));
	        model.addAttribute("obj", cust);
	        return "cust_edit";
	    }
	    
	    @RequestMapping("/update")
	    public String update(Customer obj) throws Exception {
	    	obj.setPassword(Encryption.encrypt(obj.getPassword()));
	        repo.save(obj);
	        return "redirect:/customer/home/";
	    }

		@GetMapping("/customer")
		public String customerPage(Model model) throws JsonProcessingException {
			Iterable<Customer> all = customerRepo.findAll();
			String json = new ObjectMapper().writeValueAsString(all);
			model.addAttribute("customerDataJson", json);
			return "customer";
		}
}
