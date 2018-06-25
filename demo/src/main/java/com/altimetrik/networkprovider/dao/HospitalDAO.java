package com.altimetrik.networkprovider.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.altimetrik.networkprovider.model.Hospital;
import com.altimetrik.networkprovider.model.Physician;

@Component
public class HospitalDAO extends DAO {

	public List<Hospital> getHospitals() {
		try {
			begin();
			Query hqlquery = getSession().createQuery("from Hospital");
			List<Hospital> hospitalList = hqlquery.list();

			commit();
			close();
			return hospitalList;

		} catch (HibernateException e) {
			rollback();
		}
		return null;
	}

	public Hospital getHospital(String id) {
		try {
			begin();
			Query hqlquery = getSession().createQuery("from Hospital where id = :hospId");
			hqlquery.setParameter("hospId", id);
			
			List<Hospital> list = hqlquery.list();

			Hospital h = list.get(0);
			commit();
			return h;

		} catch (HibernateException e) {
			rollback();
		} finally {
			close();
		}
		return null;
	}

	public List<Physician> getHospitalPhysicians(String id) {
		return getHospital(id).getPhysicians();
		
	}

}
