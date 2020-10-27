/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.prova.sakila.model.main;

import com.accenture.prova.sakila.model.Actor;
import com.accenture.prova.sakila.dao.ActorDao;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 *
 * @author michela.morgillo
 */
public class TestActor {

    private static final Logger log = Logger.getLogger(TestActor.class);

    public static void main(String[] args) {

        ActorDao actorDao = new ActorDao();
/*
        actorDao.retriveAllActorsByNamedQuery();

        System.out.println("  ");

        actorDao.retriveActorByIdByNamedQuery(1);

        System.out.println("  ");
        
        actorDao.retriveActorById(1);

        Actor inser = new Actor();  
        inser.setActorId(998);
        inser.setFirstName("Assunta");
        inser.setLastName("Mauro");
        actorDao.insertActor(inser);

        System.out.println("  ");

        Actor mod = new Actor();
        mod.setFirstName("Paola");
        mod.setLastName("Delle cave");
        mod.setActorId(4);
        actorDao.updateActor(mod);

        System.out.println("  ");

        Actor canc = new Actor(); 
        canc.setActorId(999);
        actorDao.deleteActor(canc);

        System.out.println("  ");
 
        actorDao.callFuctionTestOne();
        
        */
        
        actorDao.callFunctionTestTwo();
       
    }


}
