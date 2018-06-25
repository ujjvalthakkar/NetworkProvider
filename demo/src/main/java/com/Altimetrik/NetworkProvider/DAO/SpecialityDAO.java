package com.Altimetrik.NetworkProvider.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.Altimetrik.NetworkProvider.model.Hospital;
import com.Altimetrik.NetworkProvider.model.Physician;
import com.Altimetrik.NetworkProvider.model.Speciality;

@Component
public class SpecialityDAO extends DAO {

	public List<Speciality> getSpecialities() {
		try {
			begin();
			Query hqlquery = getSession().createQuery("from Speciality");
			List<Speciality> specialityList = hqlquery.list();

			commit();
			close();
			return specialityList;

		} catch (HibernateException e) {
			rollback();
		}
		return null;
	}

	public Speciality getSpeciality(String id) {
		try {
			begin();
			Query hqlquery = getSession().createQuery("from Speciality where id = :phyId");
			hqlquery.setParameter("phyId", id);
			List<Speciality> specialityList = hqlquery.list();

			commit();
			close();
			return specialityList.get(0);

		} catch (HibernateException e) {
			rollback();
		}
		return null;
	}

}
