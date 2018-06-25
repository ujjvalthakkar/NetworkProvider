package com.altimetrik.networkprovider.controllers;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.networkprovider.model.Hospital;
import com.altimetrik.networkprovider.model.Physician;
import com.altimetrik.networkprovider.model.Speciality;
import com.altimetrik.networkprovider.service.ProviderService;
import com.socrata.api.HttpLowLevel;
import com.socrata.api.Soda2Consumer;
import com.socrata.builders.SoqlQueryBuilder;
import com.socrata.exceptions.LongRunningQueryException;
import com.socrata.exceptions.SodaError;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RestController
public class NetworkProviderController {

	@Autowired
	private ProviderService providerService;

	Client client = Client.create();

	private final String getHospitalsUrl = "https://data.medicare.gov/resource/rbry-mqwu.json";
	private final String getPhysiciansUrl = "https://data.medicare.gov/resource/c8qv-268j.json";
	private final String getSpecialitiesUrl = "";
	private final Soda2Consumer consumer = Soda2Consumer.newConsumer("https://data.medicare.gov");

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

	// Physicians Code Section

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

	@RequestMapping(value = "/hospitals/{requestParam}/{value}", produces = "application/json")
	public String getHospitalByRequest(@PathVariable String requestParam, @PathVariable String value)
			throws ParseException, LongRunningQueryException, SodaError {

		// SODA Consumer API for Hospital
		SoqlQueryBuilder query = new SoqlQueryBuilder();
		switch (requestParam) {
		case "location":
			query.setWhereClause("city like '%25" + value.toUpperCase() + "%25'");
			break;
		case "name":
			query.setWhereClause("hospital_name like '%25" + value.toUpperCase() + "%25'");
			break;
		case "id":
			query.setWhereClause("provider_id like '%25" + value.toUpperCase() + "%25'");
			break;

		default:
			break;
		}

		ClientResponse response = consumer.query("rbry-mqwu", HttpLowLevel.JSON_TYPE, query.build());
		String payload = response.getEntity(String.class);

		return payload;
	}

	// Physicians Code Section

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

	@RequestMapping(value = "/physicians/{requestParam}/{value}", produces = "application/json")
	public String getPhysiciansByRequest(@PathVariable String requestParam, @PathVariable String value)
			throws ParseException, LongRunningQueryException, SodaError {

		SoqlQueryBuilder query = new SoqlQueryBuilder();
		switch (requestParam) {
		case "location":
			query.setWhereClause("cty like '%25" + value.toUpperCase() + "%25'");
			break;
		case "speciality":
			query.setWhereClause("pri_spec like '%25" + value.toUpperCase() + "%25'");
			break;	
		case "id":
			query.setWhereClause("npi like '%25" + value.toUpperCase() + "%25'");
			break;

		default:
			break;
		}

		ClientResponse response = consumer.query("c8qv-268j", HttpLowLevel.JSON_TYPE, query.build());
		String payload = response.getEntity(String.class);

		return payload;
	}

	// Specialities Code Section

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
