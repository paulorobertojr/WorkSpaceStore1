package com.wsstore.workspacestore.models;

import com.wsstore.workspacestore.entidades.Livros;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class ModelLivros {
    public static EntityManager openDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WSStore-PU");
        return emf.createEntityManager();
    }

 public boolean salvar(Livros l) {
        EntityManager em = ModelLivros.openDB(); //Inicío da conexão.

        try {
            em.getTransaction().begin();//Início da transação.

            if (l.getId() == null) {
                em.persist(l);//Monta um insert into.

            } else {
               em.merge(l);//Monta um update.
            }
            em.getTransaction().commit();//Executa o insert ou update com o objeto l.
            return true;
            
        } catch (Exception e) {
            em.getTransaction().rollback();//Desfaz a transação e volta ao estado inicial.
            return false;
            
        } finally {
            em.close();//Fecha conexão.
           
        }
    }

public List<Livros> listaLivros() {
        EntityManager em = ModelLivros.openDB(); //Abre a conexão
        try {
            return em.createQuery("select l from Livros l order by l.titulo").getResultList();
        } finally {
            em.close();
        }
    }

    public void remove(Long id){
        EntityManager em = ModelLivros.openDB(); //Abre a conexão
        try {
            Livros l = em.find(Livros.class, id);
            em.getTransaction().begin();
            em.remove(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}