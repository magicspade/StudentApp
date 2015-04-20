package com.matteo.studentapp.models;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class ProjectEJB {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentApp");
	private EntityManager em = emf.createEntityManager() ;
		
	@SuppressWarnings("unchecked")
	public List<Project> findProjects() throws Exception {
		Query query = em.createQuery("SELECT p FROM Project p");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> findProjectsFromStudent(long id) throws Exception {
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.ownerID LIKE :id ");
		query.setParameter("id", id);
		return query.getResultList();

	}
	
	public Project findProjectById(long id) throws Exception {
		return em.find(Project.class, id);
	}
	
	public void addProject(Project p) throws Exception {
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
	}
	
	public void deleteProject(Project p) throws Exception {
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
	}

	public void updateProject(Project p) throws Exception {
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
	}
}