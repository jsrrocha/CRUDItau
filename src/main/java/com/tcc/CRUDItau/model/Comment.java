package com.tcc.CRUDItau.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Comment")
public class Comment {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String userName;
	private Long userPhone;
	private Boolean userPhoneWithWhats;
	private Date date; 
	private String link;
	private String comment;
	private Boolean notificationActive;
	@ManyToOne
	@JsonIgnore
	private Pet pet;
	@ManyToOne
	@JsonIgnore
	private User userReceived;

	public Comment() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(Long userPhone) {
		this.userPhone = userPhone;
	}
	
	public Boolean getUserPhoneWithWhats() {
		return userPhoneWithWhats;
	}

	public void setUserPhoneWithWhats(Boolean userPhoneWithWhats) {
		this.userPhoneWithWhats = userPhoneWithWhats;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getNotificationActive() {
		return notificationActive;
	}

	public void setNotificationActive(Boolean notificationActive) {
		this.notificationActive = notificationActive;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public User getUserReceived() {
		return userReceived;
	}

	public void setUserReceived(User userReceived) {
		this.userReceived = userReceived;
	}
	
}
