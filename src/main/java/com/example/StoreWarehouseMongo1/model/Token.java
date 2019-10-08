/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.StoreWarehouseMongo1.model;

/**
 *
 * @author Tasos
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="token")
public class Token {

	@Id
	private String id;
	private String alphanumeric;
	private String usersId;
	private String username;
	private Boolean enabledUser;
	 
	public Token() {}
	
	public Token( String alphanumeric, String usersId, String username, Boolean enabledUser) {
		this.alphanumeric = alphanumeric;
		this.usersId = usersId;
		this.username=username;
		this.enabledUser=enabledUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getAlphanumeric() {
		return alphanumeric;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAlphanumeric(String alphanumeric) {
		this.alphanumeric = alphanumeric;
	}
	public String getUsersId() {
		return usersId;
	}
	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	public Boolean getEnabledUser() {
		return enabledUser;
	}

	public void setEnabledUser(Boolean enabledUser) {
		this.enabledUser = enabledUser;
	}
	
	
	
	
}

