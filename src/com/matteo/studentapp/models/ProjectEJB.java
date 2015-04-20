package com.matteo.studentapp.models;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class ProjectEJB {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectApp");
	private EntityManager em = emf.createEntityManager() ;
		
	@SuppressWarnings("unchecked")
	public List<Project> findProjects() throws Exception {
		em.getTransaction().begin();
		Query query = em.createQuery("SELECT * FROM Project");
		em.getTransaction().commit();
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> findProjectsFromStudent(long id) throws Exception {
		em.getTransaction().begin();
		Query query = em.createQuery("SELECT * FROM Project WHERE ownerID = " + id);
		List<Project> res = query.getResultList();
		em.getTransaction().commit();
		return res;
	}
	
	public Project findProjectById(long id) throws Exception {
		em.getTransaction().begin();
		Project res = em.find(Project.class, id);
		em.getTransaction().commit();
		return res;
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