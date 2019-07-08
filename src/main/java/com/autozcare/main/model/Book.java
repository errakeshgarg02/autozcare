package com.autozcare.main.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Book implements Serializable {

	private static final long serialVersionUID = 569764319031174436L;
	private String id;
	private String name;
	
}
