package com.materials.client.model;

import java.io.Serializable;
import java.util.Date;

public class APPTicket implements Serializable {

	private static final long serialVersionUID = 4153626654897480797L;

	private Date creationDate;
	private UserSO userLogged;

	public APPTicket() {
	}

	public APPTicket(UserSO userLogged) {
		this.userLogged = userLogged;
	}

	public APPTicket(Date date) {
		this.creationDate = date;
	}

	public UserSO getUserLogged() {
		return userLogged;
	}

	public void setUserLogged(UserSO userLogged) {
		this.userLogged = userLogged;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isValid() {
		long actual = new Date().getTime();
		long created = creationDate.getTime();
		if ((actual - created) > 1200000) {// 20min
			// if ((actual - created) > 120000) {// 2min
			return false;
		} else {
			return true;
		}
	}

}
