package com.wsstore.workspacestore.models;

import com.wsstore.workspacestore.entidades.Cliente;
import java.util.List;
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

    public List<Cliente> listaCliente() {
        EntityManager em = ModelCliente.openDB(); //Abre a conexão
        try {
            return em.createQuery("select c from Cliente c order by c.nome").getResultList();
        } finally {
            em.close();
        }
    }
    public void remove(Long id){
        EntityManager em = ModelCliente.openDB(); //Abre a conexão
        try {
            Cliente c = em.find(Cliente.class, id);
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}
