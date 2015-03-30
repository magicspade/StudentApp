package com.matteo.studentapp.models;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class StudentEJB {
	private EntityManager em;
	
	public void findStudents() {
		
	}
	/*
	public Student findStudentById(Student x) {
		
	}*/
}
