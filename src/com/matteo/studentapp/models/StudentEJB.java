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
		em.getTransaction().begin();
		Query query = em.createQuery("SELECT * FROM Student");
		List<Student> res = query.getResultList();
		em.getTransaction().commit();
		return res;
	}
	
	public Student findStudentById(long id) throws Exception {
		em.getTransaction().begin();
		Student res = em.find(Student.class, id);
		em.getTransaction().commit();
		return res;
	}
	
	public void addStudent(Student s) throws Exception {
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
	}
	
	public void deleteStudent(Student s) throws Exception {
		em.getTransaction().begin();
		em.remove(s);
		em.getTransaction().commit();
	}

	public void updateStudent(Student s) throws Exception {
		em.getTransaction().begin();
		em.merge(s);
		em.getTransaction().commit();
	}
}
