package com.wsstore.workspacestore.models;

import com.wsstore.workspacestore.entidades.Funcionario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ModelFuncionario {
    
        public static EntityManager openDB() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WSStore-PU");
        return emf.createEntityManager();

    }
public boolean salvar(Funcionario f) {
        EntityManager em = ModelCliente.openDB(); //Inicío da conexão.

        try {
            em.getTransaction().begin();//Início da transação.

            if (f.getId() == null) {
                em.persist(f);//Monta um insert into.

            } else {
               em.merge(f);//Monta um update.
            }
            em.getTransaction().commit();//Executa o insert ou update com o objeto f.
            return true;
            
        } catch (Exception e) {
            em.getTransaction().rollback();//Desfaz a transação e volta ao estado inicial.
            return false;
            
        } finally {
            em.close();//Fecha conexão.
           
        }
    }

    public Funcionario buscaEmail(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     
        public List<Funcionario> listaFuncionario() {
        EntityManager em = ModelFuncionario.openDB(); //Abre a conexão
        try {
            return em.createQuery("select f from Funcionario f order by f.nomeFunc").getResultList();
        } finally {
            em.close();
        }
    }
        
    public void remove(Long id){
        EntityManager em = ModelFuncionario.openDB(); //Abre a conexão
        try {
            Funcionario f = em.find(Funcionario.class, id);
            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
}