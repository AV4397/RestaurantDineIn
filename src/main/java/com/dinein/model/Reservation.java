package com.dinein.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Reservation")
public class Reservation {
	
	@Id
	String id;
	String reservationId;
	String customerId;
	int tableNo;
	String bdate;
	String slotTime;
	String staffId;
	String status;
	
	
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reservation(String id, String reservationId, String customerId, int tableNo, String bdate, String slotTime,
			String staffId, String status) {
		super();
		this.id = id;
		this.reservationId = reservationId;
		this.customerId = customerId;
		this.tableNo = tableNo;
		this.bdate = bdate;
		this.slotTime = slotTime;
		this.staffId = staffId;
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReservationId() {
		return reservationId;
	}
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public int getTableNo() {
		return tableNo;
	}
	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public String getSlotTime() {
		return slotTime;
	}
	public void setSlotTime(String slotTime) {
		this.slotTime = slotTime;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", reservationId=" + reservationId + ", customerId=" + customerId
				+ ", tableNo=" + tableNo + ", bdate=" + bdate + ", slotTime=" + slotTime + ", staffId=" + staffId
				+ ", status=" + status + "]";
	}
	

}
