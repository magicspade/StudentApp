package com.matteo.studentapp.models;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class StudentEJB {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentApp");
	private EntityManager em = emf.createEntityManager() ;
	
	@SuppressWarnings("unchecked")
	public List<Student> findStudents() throws Exception {
		Query query = em.createQuery("SELECT s FROM Student s");
		return query.getResultList();
	}
	
	public Student findStudentById(long id) throws Exception {
		return em.find(Student.class, id);
	}
	
	public void addStudent(Student s) throws Exception {
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
	}
	
	public void deleteStudent(Student s) throws Exception {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Project p WHERE p.ownerID = :id");
		query.setParameter("id", s.getStudentID());
		query.executeUpdate();
		em.remove(s);
		em.getTransaction().commit();
	}

	public void updateStudent(Student s) throws Exception {
		em.getTransaction().begin();
		em.merge(s);
		em.getTransaction().commit();
	}
}
