package com.Altimetrik.NetworkProvider.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "speciality")
public class Speciality {

	@Id
	@Column(name = "specialityId", unique = true, nullable = false)
	private String specialityId;

	@Column(name = "name", nullable = false)
	private String specialityName;
	
	@ManyToMany(mappedBy = "specialities")
    private List<Physician> physicians = new ArrayList<>();

	public String getId() {
		return specialityId;
	}

	public void setId(String id) {
		this.specialityId = id;
	}

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

}
