package com.wsstore.workspacestore.models;

import com.wsstore.workspacestore.entidades.Locacoes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ModelLocacoes {
   
    public static EntityManager openDB() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WSStore-PU");
        return emf.createEntityManager();

    }

    public boolean salvar(Locacoes s) {
        EntityManager em = ModelLocacoes.openDB(); //Inicío da conexão.

        try {
            em.getTransaction().begin();//Início da transação.

            if (s.getId() == null) {
                em.persist(s);//Monta um insert into.

            } else {
               em.merge(s);//Monta um update.
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
    
    public List<Locacoes> listaLocacoes() {
        EntityManager em = ModelLocacoes.openDB(); //Abre a conexão
        try {
            return em.createQuery("select s from Locacoes s order by s.nomeCliente").getResultList();
        } finally {
            em.close();
        }
    }
    
    public void remove(Long id){
        EntityManager em = ModelLocacoes.openDB(); //Abre a conexão
        try {
            Locacoes s = em.find(Locacoes.class, id);
            em.getTransaction().begin();
            em.remove(s);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}
