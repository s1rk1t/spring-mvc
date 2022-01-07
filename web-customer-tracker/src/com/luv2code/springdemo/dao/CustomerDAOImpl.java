package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// inject the Hibernate session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		// get current Hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create query to return all Customer objects in the db and sort by last name,
		// then first name
		Query<Customer> query = currentSession.createQuery("from Customer order by lastName desc, firstName",
				Customer.class);

		// execute query and get the resulting list of Customers
		List<Customer> customers = query.getResultList();

		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {

		Session currentSession = sessionFactory.getCurrentSession();

		// saves if primary key is null, updates otherwise
		currentSession.saveOrUpdate(customer);

	}

	@Override
	public Customer getCustomer(int id) {

		Session currentSession = sessionFactory.getCurrentSession();

		Customer customer = currentSession.get(Customer.class, id);

		return customer;
	}

	@Override
	public void deleteCustomer(int id) {

		Session sesh = sessionFactory.getCurrentSession();

		Customer customer = sesh.get(Customer.class, id);

		sesh.delete(customer);

		/*
		 * Another way of doing this:
		 * 
		 * 62 Query query =
		 * sesh.createQuery("delete from Customer where id=:customerId", Customer.class);
		 * 
		 * 64 query.setParameter("customerId", id);
		 * 
		 * 66 query.executeUpdate();
		 * 
		 */

	}

}
