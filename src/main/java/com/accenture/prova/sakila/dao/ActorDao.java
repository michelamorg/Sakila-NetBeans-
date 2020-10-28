/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.prova.sakila.dao;

import java.util.List;
import com.accenture.prova.sakila.model.Actor;
import java.time.Clock;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TransactionRequiredException;
import lombok.Data;
import org.apache.log4j.Logger;
import org.postgresql.core.TransactionState;

/**
 *
 * @author michela.morgillo
 */
@Data
public class ActorDao {

    private static final Logger log = Logger.getLogger(ActorDao.class);

    private static final String PERSISTENCE_UNIT = "demo_pu";
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction transaction;

    public ActorDao() {
        this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        this.em = emf.createEntityManager();

    }

    //metodo recupera tutti gli attori
    public void retriveAllActorsByNamedQuery() {
        List<Actor> listActors = em.createNamedQuery("Actor.findAll", Actor.class).getResultList();  //richiamo la query dall'entity
        listActors.stream().forEach(p -> System.out.println(":" + p.toString()));//metodo stream che mi legge la sequenza della lista actor e itera tramite un predicato p

    }

    //metodo ricerca tramite id
    public void retriveActorByIdByNamedQuery(Integer id) {
        em.createNamedQuery("Actor.findByActorId", Actor.class); //richiamo la query nell'entity
        Actor actById = em.find(Actor.class, id); //trovo tramite la classe Entity e la Pk
        System.out.println("Actor" + actById.toString());
    }

    //metodo ricerca tramite id non con il named query
    public void retriveActorById(Integer id) {
        Actor actor = em.find(Actor.class, id);
        System.out.println("Actor" + actor.toString());

    }

    //metodo per inserire  ///// 
    public void insertActor(Actor actor) {
        try {
            em.getTransaction().begin();//inizializzo em e avvio la transazione
            em.persist(actor); //questo metodo viene utilizzato per rendere un'istanza gestita e persistente.

            log.info(actor.getActorId() + actor.getFirstName() + actor.getLastName());

        } catch (IllegalStateException | TransactionRequiredException Iex) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            em.getTransaction().commit(); //chiudo transazione

            em.close();
            emf.close();

        }

    }

    //metodo per modificare 
    public void updateActor(Actor actor) {
        try {

            //prima verificare se esiste l'id
            Actor act = em.find(Actor.class, actor.getActorId());
            em.getTransaction().begin();

            log.info(actor.getActorId() + actor.getFirstName() + actor.getLastName() + actor.getLastUpdate());
            if (act != null) {
                em.merge(actor); //uso per per modificare
                log.info("modifica Effettuata!");
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

    public void deleteActor(Actor actor) {
        try {
            Actor act = em.find(Actor.class, actor.getActorId());//prima viene recuperato tramite id
            em.getTransaction().begin(); //poi immesso in una transazione attiva

            if (act != null) {
                Actor del = em.merge(actor);
                em.remove(del); //poi eliminato con remove
                log.info("l'attore è stato rimosso!");
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

//SP
    public void callFuctionTestOne() { //richiamo la procedura dal codice
        StoredProcedureQuery sp = em.createStoredProcedureQuery("name_act")
                .registerStoredProcedureParameter("v_id", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nome_attore", String.class, ParameterMode.OUT);

        sp.setParameter("v_id", 2);
        sp.execute();

        String actNome = (String) sp.getOutputParameterValue("nome_attore");
        System.out.println(actNome);
        log.info("funzione eseguita per id 2!");

    }

    public void callFunctionTestTwo() {
        StoredProcedureQuery sp = em.createStoredProcedureQuery("name_act", Actor.class)
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

        sp.setParameter(1, 5);
        sp.execute();

        String actNome = (String) sp.getOutputParameterValue(2);
        System.out.println(actNome);
        log.info("funzione eseguita per id 5!");

    }

}
