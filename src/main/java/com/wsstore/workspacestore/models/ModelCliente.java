package com.wsstore.workspacestore.models;

import com.wsstore.workspacestore.entidades.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ModelCliente {

    public static EntityManager openDB() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WSStore-PU");
        return emf.createEntityManager();

    }

    public boolean salvar(Cliente c) {
        EntityManager em = ModelCliente.openDB(); //Inicío da conexão.

        try {
            em.getTransaction().begin();//Início da transação.

            if (c.getId() == null) {
                em.persist(c);//Monta um insert into.

            } else {
               em.merge(c);//Monta um update.
            }
            em.getTransaction().commit();//Executa o insert ou update com o objeto c.
            return true;
            
        } catch (Exception e) {
            em.getTransaction().rollback();//Desfaz a transação e volta ao estado inicial.
            return false;
            
        } finally {
            em.close();//Fecha conexão.
           
        }
    }

}
