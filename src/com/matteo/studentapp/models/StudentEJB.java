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
		Query query = em.createQuery("SELECT * FROM Student s");
		return query.getResultList();
	}
	
	public Student findStudentById(long id) throws Exception {
		return em.find(Student.class, id);
	}
	
	public void addStudent(Student s) throws Exception {
		em.persist(s);
	}
	
	public void deleteStudent(Student s) throws Exception {
		em.remove(s);
	}

	public void updateStudent(Student s) throws Exception {
		em.merge(s);
	}
}
