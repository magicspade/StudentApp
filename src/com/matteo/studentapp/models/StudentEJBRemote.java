package com.matteo.studentapp.models;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface StudentEJBRemote {
	
	List<Student> findStudents()  throws Exception ;
	Student findStudentById(long id)  throws Exception ;
	void addStudent(Student s)  throws Exception ;
	void deleteStudent(Student s)  throws Exception;
	void updateStudent(Student s)  throws Exception ;
	
}