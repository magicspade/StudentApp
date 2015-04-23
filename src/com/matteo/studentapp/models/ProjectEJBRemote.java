package com.matteo.studentapp.models;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ProjectEJBRemote {	
	List<Project> findProjects()  throws Exception ;
	List<Project> findProjectsFromStudent(long id) throws Exception;
	Project findProjectById(long id)  throws Exception ;
	void addProject(Project s)  throws Exception ;
	void deleteProject(Project s)  throws Exception;
	void updateProject(Project s)  throws Exception ;
	
}