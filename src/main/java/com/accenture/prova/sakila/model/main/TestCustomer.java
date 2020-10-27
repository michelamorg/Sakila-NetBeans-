/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.prova.sakila.model.main;

import com.accenture.prova.sakila.dao.CustomerDao;
import com.accenture.prova.sakila.model.Customer;
import org.apache.log4j.Logger;

/**
 *
 * @author michela.morgillo
 */
public class TestCustomer {

    private static final Logger log = Logger.getLogger(TestCustomer.class);

    public static void main(String[] args) {

        CustomerDao customerDao = new CustomerDao();
        /*
        customerDao.retriveAllCustomers();

        System.out.println("  ");

        customerDao.retriveCustomerById(594);

        System.out.println("  ");

        customerDao.retriveCustomerByNamedQueryId(599);

      
    
       
     
        
        
        System.out.println(" ");
        
        Customer canc = new Customer();
        canc.setCustomerId(888);
        customerDao.removeCustomer(canc);
      
        System.out.println(" ");
        
        customerDao.retriveAllCustomerFirstName();
        
        customerDao.orderByDescCustomer();
        
          System.out.println("  ");
        


        ///TIPO DATE 
        Customer inser = new Customer(); ///
        inser.setCustomerId(888);
        inser.setStoreId(0);
        inser.setFirstName("Antonio");
        inser.setLastName("De luca");
        inser.setEmail("antonio.deluca@gmail.com");
        inser.setActivebool(true);
        inser.setActive(3);
        inser.setCreateDate();
        inser.setLastUpdate();
        customerDao.insertCustomer(inser);
 

        Customer mod = new Customer();///
        mod.setFirstName("Massimo");
        mod.setLastName("Grimaldi");
        mod.setCustomerId(888);
        customerDao.updateCustomer(mod);
        

        customerDao.infoCustomer(); ///
*/
        
        
        //DA VERIFICARE 
        customerDao.CustomerWhere();

        customerDao.esempiVariJPQL();

        customerDao.jpqlQueryAll();

        customerDao.esempiJpqldml();
        
        customerDao.jpqlFiltri();
        
        customerDao.aggregateAndSortingJpql();
    }

}
