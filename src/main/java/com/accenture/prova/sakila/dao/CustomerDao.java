/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.prova.sakila.dao;

import ch.qos.logback.core.CoreConstants;
import com.accenture.prova.sakila.model.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.apache.log4j.Logger;

/**
 *
 * @author michela.morgillo
 */
@Data
public class CustomerDao {

    private static final Logger log = Logger.getLogger(CustomerDao.class);

    private static final String Persistence_unit = "demo_pu";
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tran;

    public CustomerDao() {
        this.emf = Persistence.createEntityManagerFactory(Persistence_unit);
        this.em = emf.createEntityManager();
    }

    public void retriveAllCustomers() {
        List<Customer> listCust = em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
        listCust.stream().forEach(p -> System.out.println(":" + p.toString()));

    }

    public void retriveCustomerById(Integer id) {
        Customer custById = em.find(Customer.class, id);
        System.out.println("Customer:" + " " + custById.toString());

    }

    public void retriveCustomerByNamedQueryId(Integer id) {
        em.createNamedQuery("Customer.findByCustomerId", Customer.class);
        Customer custByIdN = em.find(Customer.class, id);
        System.out.println("Customer:" + " " + custByIdN.toString());
    }

    public void retriveAllCustomerFirstName() {
        em.getTransaction().begin();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> cust = cq.from(Customer.class);

        cq.select(cust.get("firstName")); //se voglio più campi uso multiselect

        CriteriaQuery<Customer> select = cq.select(cust);

        TypedQuery<Customer> q = em.createQuery(select);

        
        
        List<Customer> list = q.getResultList();

        System.out.println("I nomi di tutti i customer sono :");

        for (Customer c : list) {
            System.out.println(c.getFirstName());

        }

        em.getTransaction().commit();
        em.close();
        emf.close();

    }

    public void orderByDescCustomer() {
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> cust = cq.from(Customer.class);

        cq.orderBy(cb.desc(cust.get("customerId"))); //ordina in modo decrescente 

        CriteriaQuery<Customer> select = cq.select(cust);

        TypedQuery<Customer> q = em.createQuery(select);

        List<Customer> list = q.getResultList();

        System.out.println("i custumer in ordine decrescente per id:");

        for (Customer c : list) {

            System.out.println(c.getCustomerId() + c.getFirstName() + c.getLastName());

        }

        em.getTransaction().commit();
        em.close();
        emf.close();

    }

    //ricerca tramite nome che inizia per a ed effettua una count 
    public void infoCustomer() { ////AGGIUSTA

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> cust = cq.from(Customer.class);

        cq.multiselect(cust.get("lastName"), cb.count(cust).alias("numero di customer")).groupBy(cust.get("lastName"));

        System.out.print("lastName");
        System.out.println("\t Count");

        /*          List<Object[]> list = em.createQuery(cq).getResultList();  
        
for(Object[] object : list){  
    System.out.println(object[0] + " " + object[1]);  
  
}  

        em.getTransaction().commit();
        em.close();
        emf.close();
         */
    }

    public void CustomerWhere() { ///Verifica
        em.getTransaction().begin();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        AbstractQuery<Customer> cq = cb.createQuery(Customer.class);

        Root<Customer> cust = cq.from(Customer.class);

        cq.where(cb.lessThan(cust.get("customerId"), 22));  //selezionare i customer con id <a 22

        CriteriaQuery<Customer> select = ((CriteriaQuery<Customer>) cq).select(cust);

        TypedQuery<Customer> tq = em.createQuery(select);

        List<Customer> list = tq.getResultList();

        System.out.println("Customer con id inferiore a 22");

        for (Customer c : list) {
            System.out.println(c.getCustomerId() + c.getFirstName() + c.getLastName());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public void insertCustomer(Customer cust) { ///dato date 
        try {
            em.getTransaction().begin();
            em.persist(cust);

            log.info(cust.getCustomerId() + " " + cust.getFirstName() + " " + cust.getLastName() + " " + cust.getEmail());
        } catch (IllegalStateException | TransactionRequiredException Iex) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            em.getTransaction().commit();

            em.close();
            emf.close();

        }
    }

    public void updateCustomer(Customer cust) { ///dato date 
        try {

            Customer customer = em.find(Customer.class, cust.getCustomerId());
            em.getTransaction().begin();

            if (customer != null) {
                em.merge(cust);
                log.info("esiste e può essere modificato!");
            } else {
                log.info("non esiste!");
            }
        } catch (IllegalStateException | TransactionRequiredException Iex) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            em.getTransaction().commit();

            em.close();
            emf.close();

        }

    }

    public void removeCustomer(Customer cust) {
        try {
            Customer c = em.find(Customer.class, cust.getCustomerId());

            em.getTransaction().begin();

            if (c != null) {
                em.remove(cust);
                log.info("Esiste e quindi si può eliminare!");
            } else {
                log.info("Non esiste !");
            }
        } catch (IllegalStateException | TransactionRequiredException Iex) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            em.getTransaction().commit();

            em.close();
            emf.close();

        }
    }

    //esempi jpql
    public void esempiVariJPQL() {
        em.getTransaction().begin();
        //QUERY DINAMICA
        //1 metodo per eseguire un'istruzione jpql 
        Query cq = em.createQuery("SELECT c.last_name FROM Customer c"); //tipo di query che restituisce una lista di cognomi
        List<Customer> listLastName = cq.getResultList();
        System.out.println("I cognomi dei Customer sono:");

        for (Customer c : listLastName) { //itero lista
            System.out.println(c);

        }

        System.out.println("---------");
        //QUERY STATICA 
        //2 metodo per query denominate
        List<Customer> listLastN = em.createNamedQuery("Customer.findAllLastName", Customer.class).getResultList();
        listLastN.stream().forEach(p -> System.out.println("Il risultato della query denominata è:" + " " + p.toString()));//itero lista

        Customer index = listLastN.get(3);
        System.out.println("il customer all'indice 3 è" + " " + index);

        System.out.println("---------");

        //3 query lista nomi e email 
        TypedQuery<Customer> tp = em.createQuery("Customer.findAllFirstNameAndEmail", Customer.class);//mi restituisce una lista 
        List<Customer> listCust = tp.getResultList();

        System.out.println("I cognomi dei Customer sono:");
        for (Customer cust : listCust) {

            System.out.println(cust);

        }

        int numList = listCust.size();
        System.out.println("la dimensione della lista è di:" + " " + numList);

        //query di modifica customer
        em.getTransaction().commit();
        em.close();
        emf.close();

    }

    public void jpqlQueryAll() {
        //query su più colonne , recupera tutti i custumer 
        em.getTransaction().begin();
        Query all = em.createQuery("SELECT c FROM customer c"); //ritorna una lista 

        List<Customer> listCust = all.getResultList();

        System.out.println("la lista di tutti i customer è:");

        for (Customer list : listCust) {

            System.out.println(list.getCustomerId() + list.getFirstName() + list.getLastName() + list.getEmail());

        }

        em.getTransaction().commit();
        em.close();
        emf.close();

    }

    public void esempiJpqldml() {
        //
        em.getTransaction().begin();

        Query up = em.createQuery("update customer set first_name= 'Giacomo' where customer_id=352"); //modifica
        Query del = em.createQuery("delete from customer where customer_id=999");

        del.executeUpdate();
        up.executeUpdate();

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    //seleziona i customer con id tra 300 e 345
    //seleziona i customer che hanno un nome che inizia per S
    public void jpqlFiltri() {
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT c FROM Customer c WHERE c.customer_id between 340 and 345");

        List<Customer> listF = q.getResultList();

        System.out.println("I Customer con id tra 340 e 345 sono:");

        for (Customer c : listF) {
            System.out.println(c.getCustomerId() + c.getFirstName() + c.getLastName());

        }

        Query q1 = em.createQuery("SELECT c FROM Customer c WHERE c.first_name like 'S%'");

        List<Customer> listF2 = q1.getResultList();

        System.out.println("I Customer con nome che inizia per la lettera S sono:");

        for (Customer c : listF2) {
            System.out.println(c.getCustomerId() + c.getFirstName() + c.getLastName());

        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public void aggregateAndSortingJpql() {

        em.getTransaction().begin();

        Query count = em.createQuery("SELECT count(c) FROM Customer c"); //ottengo un solo risultato
        System.out.println("Il numero dei customer è:" + count.getSingleResult());

        Query order = em.createQuery("SELECT c FROM Customer c order by c.customer_id desc ");
        List<Customer> list = order.getResultList();
        System.out.println("I customer ordinati in modo decrescente:");
        for (Customer c : list) {

            System.out.println(c.getCustomerId() + c.getFirstName() + c.getLastName());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();

    }

}
