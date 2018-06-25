package com.altimetrik.networkprovider.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.altimetrik.networkprovider.model.Hospital;
import com.altimetrik.networkprovider.model.Physician;

@Component
public class PhysicianDAO extends DAO {

	public List<Physician> getPhysicians() {
		try {
			begin();
			Query hqlquery = getSession().createQuery("from Physician");
			List<Physician> physicianList = hqlquery.list();

			commit();
			close();
			return physicianList;

		} catch (HibernateException e) {
			rollback();
		}
		return null;
	}

	public Physician getPhysician(String id) {
		try {
			begin();
			Query hqlquery = getSession().createQuery("from Physician where id = :phyId");
			hqlquery.setParameter("phyId", id);
			List<Physician> physiciansList = hqlquery.list();

			commit();
			close();
			return physiciansList.get(0);

		} catch (HibernateException e) {
			rollback();
		}
		return null;
	}

}
