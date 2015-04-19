package com.matteo.studentapp.models;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface StudentEJBRemote {
	
	List<Student> findStudents()  throws Exception ;
	Student findStudentById(long id)  throws Exception ;
	Student createStudent(Student s)  throws Exception ;
	void deleteStudent(Student s)  throws Exception;
	Student updateStudent(Student s)  throws Exception ;
	
}