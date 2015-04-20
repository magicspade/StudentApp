package com.matteo.studentapp.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Student implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long studentID; 
	 private String name;
	 private String address;
	 private int age; 
	 	
	 public Long getStudentID() {
		 return studentID;
	 }
	 
	 public void setStudentID(Long studentID) {
		 this.studentID = studentID;
	 }
	 
	 public String getName() {
		 return name;
	 }
	
	 public void setName(String name) {
		 this.name = name;
	 }
	 
	 public String getAddress() {
		 return address;
	 }
	
	 public void setAddress(String address) {
		 this.address = address;
	 }
	
	 public int getAge() {
		 return age;
	 }
	
	 public void setAge(int age) {
		 this.age = age;
	 }

}
