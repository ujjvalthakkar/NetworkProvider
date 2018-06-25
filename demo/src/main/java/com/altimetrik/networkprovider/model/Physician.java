package com.altimetrik.networkprovider.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "physician")
public class Physician {

	@Id
	@Column(name = "physicianId", unique = true, nullable = false)
	private String physicianId;

	@Column(name = "name", nullable = false)
	private String physicianName;

	@ManyToMany(mappedBy = "physicians")
    private List<Hospital> hospitals = new ArrayList<>();
	
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "physicianspec", joinColumns = @JoinColumn(name = "phy_id"), inverseJoinColumns = @JoinColumn(name = "spec_id"))
	private List<Speciality> specialities = new ArrayList<>();
	
	public String getId() {
		return physicianId;
	}

	public void setId(String id) {
		this.physicianId = id;
	}

	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}

	

}
