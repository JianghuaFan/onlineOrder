package com.laioffer.onlineOrder.dao.CustomerDao;

import com.laioffer.onlineOrder.entity.Authorities;
import com.laioffer.onlineOrder.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void signUp (Customer customer){
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmail(customer.getEmail());

        Session session = null;
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
            if(session != null){
                session.getTransaction().rollback();
            }
            throw ex;
        }finally{
            if(session != null){
                session.close();
            }
        }

    }

    public Customer getCustomer(String email){
        Customer customer = null;
        Session session = null;
        try{
            session = sessionFactory.openSession();
            customer = session.get(Customer.class, email);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(session != null){
                session.close();
            }
        }
        return customer;
    }
}
