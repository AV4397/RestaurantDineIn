package com.dinein.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dinein.model.Booking;
import com.dinein.model.Reservation;
import com.dinein.model.Table;
import com.dinein.repository.PaymentRepository;
import com.dinein.repository.ReservationRepository;
import com.dinein.repository.SlotRepository;
import com.dinein.repository.StaffRepository;
import com.dinein.repository.TableRepository;

@RequestMapping("/reservation")
@Controller
public class ReservationController {

	@Autowired
	ReservationRepository repo;

	@Autowired
	TableRepository tableRepo;

	@Autowired
	PaymentRepository payRepo;

	@Autowired
	StaffRepository staffRepo;

	@Autowired
	SlotRepository slotRepo;



	@RequestMapping("/list")
	public String list(Model model, HttpServletRequest req) {
		if (req.getSession().getAttribute("usertype").equals("Waiter"))
			model.addAttribute("datalist", repo.findAllByStaffIdOrderByIdDesc(req.getSession().getAttribute("userid").toString()));
		else
			model.addAttribute("datalist", repo.findAllByOrderByIdDesc());

		model.addAttribute("waiters", staffRepo.findAllByDesignation("Waiter"));
		model.addAttribute("slots", slotRepo.findAll());
		
		return "reservation_list";
	}

	@RequestMapping("/mylist")
	public String myList( Model model, HttpServletRequest request) {
		model.addAttribute("datalist", repo.findByCustomerId(request.getSession().getAttribute("userid").toString()));
		return "reservation_mylist";
	}

	@RequestMapping("/search")
	public String search(@RequestParam String bdate, @RequestParam String slotTime, Model model, HttpServletRequest req) {
		if (req.getSession().getAttribute("usertype").equals("Waiter"))
			model.addAttribute("datalist", repo.findAllByStaffIdAndBdateAndSlotTime(req.getSession().getAttribute("userid").toString(), bdate, slotTime));
		else
			model.addAttribute("datalist", repo.findAllByBdateAndSlotTime(bdate,slotTime));

		model.addAttribute("slots", slotRepo.findAll());
		
		return "reservation_list";
	}
	
	@RequestMapping("/save")
	public String save(Booking obj, HttpServletRequest request) throws IOException, ParseException {

		List<Table> tables = tableRepo.findByCapacityGreaterThanEqualOrderByCapacity(obj.getCapacity());
		Optional<Reservation> ress;

		Optional<Reservation> idobj = repo.findTopByOrderByReservationIdDesc();
		System.out.println(idobj);
		String id = null;
		if (idobj.isPresent()) {

			int idnum = Integer.parseInt(idobj.get().getReservationId().substring(5));
			idnum++;
			id = "RES47" + idnum;
		} else {
			id = "RES4731842";
		}

		int tableNo = -1;

		for (Table table : tables) {
			ress = repo.findByBdateAndSlotTimeAndTableNoAndStatus(obj.getBdate(), obj.getSlotTime(), table.getTableNo(),
					"Booked");
			if (ress.isEmpty()) {
				tableNo = table.getTableNo();
				break;
			}
		}

		if (tableNo != -1) {
			Reservation robj = new Reservation();
			robj.setReservationId(id);
			robj.setBdate(obj.getBdate());
			robj.setSlotTime(obj.getSlotTime());
			robj.setTableNo(tableNo);
			robj.setCustomerId(request.getSession().getAttribute("userid").toString());
			robj.setStatus("Booked");
			robj.setStaffId(tableRepo.findByTableNo(tableNo).get().getWaiterId());
			repo.save(robj);
			return "redirect:/reservation/mylist";

		} else
			return "unavailable";

	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable String id, HttpServletRequest req) {
		Optional<Reservation> obj = repo.findById(id);
		repo.delete(obj.get());

		if (req.getSession().getAttribute("usertype").equals("Manager"))
			return "redirect:/reservation/list";
		else
			return "redirect:/reservation/mylist";

	}

	@RequestMapping("/walkin/create")
	public String walkinCreate(Model model) {
		model.addAttribute("tables", tableRepo.findAll());
		model.addAttribute("slots", slotRepo.findAll());

		return "order_walkin_create";
	}

	@RequestMapping("/walkin/save")
	public String walkinSave(@RequestParam int tableNo, @RequestParam String slotTime, HttpServletRequest request)
			throws IOException, ParseException {

		Optional<Reservation> idobj = repo.findTopByOrderByReservationIdDesc();

		String id = null;
		if (idobj.isPresent()) {

			int idnum = Integer.parseInt(idobj.get().getReservationId().substring(5));
			idnum++;
			id = "RES47" + idnum;
		} else {
			id = "RES4731842";
		}

		Reservation robj = new Reservation();
		robj.setReservationId(id);
		robj.setBdate(LocalDate.now().toString());
		robj.setSlotTime(slotTime);
		robj.setTableNo(tableNo);
		robj.setCustomerId("Direct Walkin");
		robj.setStatus("Booked");
		robj.setStaffId(tableRepo.findByTableNo(tableNo).get().getWaiterId());
		repo.save(robj);
		return "redirect:/reservation/list";

	}

	@RequestMapping("/waiter/assign")
	public String delete(@RequestParam String id, @RequestParam String staffId, HttpServletRequest req) {
		Reservation obj = repo.findById(id).get();
		obj.setStaffId(staffId);
		repo.save(obj);

		return "redirect:/reservation/list";

	}

	static String ymd_dmy(String date) throws ParseException {
		String arr[] = date.split("-");
		return arr[2] + "-" + arr[1] + "-" + arr[0];
	}
}
