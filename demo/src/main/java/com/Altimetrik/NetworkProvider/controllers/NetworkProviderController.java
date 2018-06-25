package com.Altimetrik.NetworkProvider.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Altimetrik.NetworkProvider.Service.ProviderService;

import com.Altimetrik.NetworkProvider.model.Hospital;
import com.Altimetrik.NetworkProvider.model.Physician;
import com.Altimetrik.NetworkProvider.model.Speciality;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RestController
public class NetworkProviderController {

	@Autowired
	private ProviderService providerService;

	Client client = Client.create();
	String getHospitalsByNameUrl = "https://data.medicare.gov/resource/rbry-mqwu.json?$where=hospital_name like ";

	// APIs connecting to Database
	@RequestMapping("/gethospitals")
	public List<Hospital> getTopics() {
		return providerService.getHospitals();
	}

	@RequestMapping("/gethospitals/{id}")
	public List<Physician> getHospitalPhysicians(@PathVariable String id) {
		return providerService.getHospitalPhysicians(id);
	}

	@RequestMapping("/getphysicians")
	public List<Physician> getAllPhysicians() {
		return providerService.getPhysicians();

	}

	@RequestMapping("/getspecialities")
	public List<Speciality> getAllSpecialities() {
		return providerService.getSpecialities();

	}

	// APIs connecting to Hospital, Physicians and Speciality APIs

	@RequestMapping(value = "/hospitals", produces = "application/json")
	public String getHospitals() {
		String getHospitalsUrl = "https://data.medicare.gov/resource/rbry-mqwu.json";
		WebResource webResource = client.resource(getHospitalsUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		return result;
	}

	@RequestMapping(value = "/hospitals/{name}", produces = "application/json")
	public String getHospitalByName(@PathVariable String name) {

		String getHospitalsUrl = "https://data.medicare.gov/resource/rbry-mqwu.json";
		String url = getHospitalsByNameUrl + "'%" + name.toUpperCase() + "%'";

		WebResource webResource = client.resource(getHospitalsUrl);
		webResource.queryParam("hospital_name", name);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}
		String result = response.getEntity(String.class);

		System.out.println(">>>>>>>>>>>>>>>>>>" + result);

		
		// ========
		WebResource webResource1 = client.resource(getHospitalsUrl);
		webResource1.queryParam("$where", "hospital_name like '%25" + name.toUpperCase() + "%25'");
		ClientResponse response1 = webResource1.accept("application/json").get(ClientResponse.class);
		if (response1.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response1.getStatus());
		}
		
		String result1 = response1.getEntity(String.class);
		System.out.println(">>>>>>>>" + webResource1.getURI());
		// ========

		return result;
	}

	@RequestMapping(value = "/hospitals/id/{id}", produces = "application/json")
	public String getHospitalById(@PathVariable String id) {
		String url = getHospitalsByNameUrl + "'%" + id.toUpperCase() + "%'";
		String getHospitalsUrl = "https://data.medicare.gov/resource/rbry-mqwu.json";
		WebResource webResource = client.resource(getHospitalsUrl);
		webResource.queryParam("hospital_name", id);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		return result;
	}

	@RequestMapping(value = "/physicians", produces = "application/json")
	public String getPhysicians() {

		String getPhysiciansUrl = "";

		WebResource webResource = client.resource(getPhysiciansUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		return result;
	}

	@RequestMapping(value = "/specialities", produces = "application/json")
	public String getSpecialities() {

		String getSpecialitiesUrl = "";

		WebResource webResource = client.resource(getSpecialitiesUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		return result;
	}

}
