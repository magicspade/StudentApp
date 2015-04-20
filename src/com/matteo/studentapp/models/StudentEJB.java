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
		List<Student> res = query.getResultList();
		return res;
	}
	
	public Student findStudentById(long id) throws Exception {
		Student res = em.find(Student.class, id);
		return res;
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
