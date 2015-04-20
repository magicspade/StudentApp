package com.matteo.studentapp.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Project implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long projectID; 
	 private String title;
	 private Long ownerID;
	 	
	 public Long getProjectID() {
		 return projectID;
	 }
	 
	 public void setProjectID(Long projectID) {
		 this.projectID = projectID;
	 }
	 
	 public String getTitle() {
		 return title;
	 }
	
	 public void setTitle(String name) {
		 this.title = name;
	 }
	 
	 public Long getOwnerID() {
		 return ownerID;
	 }
	
	 public void setOwnerID(Long ownerID) {
		 this.ownerID = ownerID;
	 }

}
