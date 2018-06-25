package com.Altimetrik.NetworkProvider.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.Altimetrik.NetworkProvider.DAO.HospitalDAO;
import com.Altimetrik.NetworkProvider.DAO.PhysicianDAO;
import com.Altimetrik.NetworkProvider.DAO.SpecialityDAO;
import com.Altimetrik.NetworkProvider.model.Hospital;
import com.Altimetrik.NetworkProvider.model.Physician;
import com.Altimetrik.NetworkProvider.model.Speciality;

@Service
@Component
public class ProviderService {

	@Autowired
	private HospitalDAO hospitalDAO;
	
	@Autowired
	private PhysicianDAO physicianDAO;
	
	@Autowired
	private SpecialityDAO specDAO;

	public List<Hospital> getHospitals() {

		return (List<Hospital>) hospitalDAO.getHospitals();
	}
	
	public List<Physician> getHospitalPhysicians(String id) {

		return (List<Physician>) hospitalDAO.getHospitalPhysicians(id);
	}
	
	public Hospital getHospitals(String id) {

		return (Hospital) hospitalDAO.getHospitals();
	}
	
	public List<Physician> getPhysicians() {

		return (List<Physician>) physicianDAO.getPhysicians();
	}
	
	public List<Speciality> getSpecialities() {

		return (List<Speciality>) specDAO.getSpecialities();
	}
}
