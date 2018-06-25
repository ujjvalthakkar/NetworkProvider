package com.altimetrik.networkprovider.controllers;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.networkprovider.model.Hospital;
import com.altimetrik.networkprovider.model.Physician;
import com.altimetrik.networkprovider.model.Speciality;
import com.altimetrik.networkprovider.service.ProviderService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RestController
public class NetworkProviderController {

	@Autowired
	private ProviderService providerService;

	Client client = Client.create();

	String getHospitalsUrl = "https://data.medicare.gov/resource/rbry-mqwu.json";
	String getPhysiciansUrl = "https://data.medicare.gov/resource/c8qv-268j.json";
	String getSpecialitiesUrl = "";
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

	// APIs connecting to Hospital, Physicians and Specialties APIs

	@RequestMapping(value = "/hospitals", produces = "application/json")
	public String getHospitals() {

		WebResource webResource = client.resource(getHospitalsUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		return result;
	}

	@RequestMapping(value = "/hospitals/{name}", produces = "application/json")
	public String getHospitalByName(@PathVariable String name) throws ParseException {

		String url = getHospitalsByNameUrl + "'%25" + name.toUpperCase() + "%25'";
		String url1 = getHospitalsUrl + "?hospital_name like " + name.toUpperCase();

		String url2 = getHospitalsUrl + "?q={\"hospital_name\": {\"$regex\" :\"" + name + "\"}}";

		// System.out.println("Name: " + name + " URL:" + url + " URL1:" + url1
		// + " url2:" + url2);
		WebResource webResource = client.resource(getHospitalsUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}
		String result = response.getEntity(String.class);

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(result);

		JSONObject jo = new JSONObject();
		jo.put("array", jsonArray);

		//System.out.println(">>>>>>" + jo.get("array"));

		return result;
	}

	@RequestMapping(value = "/hospitals/id/{id}", produces = "application/json")
	public String getHospitalById(@PathVariable String id) {
		//
		return "";
	}

	@RequestMapping(value = "/physicians", produces = "application/json")
	public String getPhysicians() {

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

		WebResource webResource = client.resource(getSpecialitiesUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		return result;
	}

}
