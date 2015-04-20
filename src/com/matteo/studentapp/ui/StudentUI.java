package com.matteo.studentapp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

import com.matteo.studentapp.models.Project;
import com.matteo.studentapp.models.ProjectEJB;
import com.matteo.studentapp.models.Student;
import com.matteo.studentapp.models.StudentEJB;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@Theme("studentapp")
public class StudentUI extends UI {
	
	final StudentEJB studentEjb = new StudentEJB();
	final ProjectEJB projectEjb = new ProjectEJB();

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = StudentUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {	
		
		/*try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
		}*/
				
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		
		/* TITLE */
		Label title = new Label("<h1>Student App</h1>");
		title.setContentMode(Label.CONTENT_XHTML);
		layout.addComponent(title);

		/* Tabsheet */
		TabSheet table = new TabSheet();
		
		table.addTab(studentLayout(),"Students");
		table.addTab(projectLayout(),"Projects");
		
		layout.addComponent(table);
		
	}
	
	// STUDENTS -----------------------------
	
	private Layout studentLayout() {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		
		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setMargin(true);
		toolBar.setSpacing(true);
		
		TextField filter = new TextField();
		filter.setInputPrompt("Filter Students");
		toolBar.addComponent(filter);
		
		Button add = new Button("Add Student");
		add.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				addStudent();				
			}
		});
		toolBar.addComponent(add);
		
		Button up = new Button("Update/Delete Student");
		up.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				findStudent("update");				
			}
		});
		toolBar.addComponent(up);
		
		Button showProjects = new Button("Show student projects");
		showProjects.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				findStudent("showProjects");				
			}
		});
		toolBar.addComponent(showProjects);
		
		layout.addComponent(toolBar);
		
//		List<Student> data = studentEjb.findStudents();
		// TO DELETE --------------------------
		List<Student> data = new ArrayList<Student>();
		Student lol = new Student();
		lol.setName("bob");
		lol.setAddress("12 rue paul");
		lol.setAge(42);
		lol.setStudentID(1024l);
		data.add(lol);
		// ------------------------------------
				
		Table table = new Table("Students");
		BeanItemContainer<Student> dSource = new BeanItemContainer<Student>(Student.class);
		table.setContainerDataSource(dSource);
		dSource.addAll(data);
		//Formatting
		table.setWidth("100%");
		table.setSelectable(true);
		table.setVisibleColumns( new Object[] {"studentID", "name", "age", "address"} );
		table.setColumnHeaders(new String[] {"ID", "Name", "Age", "Address"});
		layout.addComponent(table);
		
		//Filters
		filter.addTextChangeListener(new TextChangeListener() {
		    Filter filter = null;

		    public void textChange(TextChangeEvent event) {
		        Filterable f = (Filterable) dSource;
		        if (filter != null) {
		            f.removeContainerFilter(filter);
		        }
		        filter = new Or(new SimpleStringFilter("studentID", event.getText(), true, false),
		        				new SimpleStringFilter("name", event.getText(), true, false),
		        				new SimpleStringFilter("age", event.getText(), true, false),
		        				new SimpleStringFilter("address", event.getText(), true, false)
		        		);
		        f.addContainerFilter(filter);
		    }
		});
		
		return layout;
	}
	
	private void addStudent() {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		Window window = new Window("Add a new student",layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
		
		TextField nameField = new TextField("Name");
		nameField.setInputPrompt("Bob");		
		layout.addComponent(nameField);
		
		TextField addressField = new TextField("Address");
		addressField.setInputPrompt("420 paper street");		
		layout.addComponent(addressField);
		
		TextField ageField = new TextField("Age");
		ageField.setInputPrompt("32");
		layout.addComponent(ageField);
		
		Button button = new Button("Add this !");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				boolean argOk = true;
				
				String name = nameField.getValue();
				String address = addressField.getValue();
				if(name == "" || address == "") {
					argOk = false;
					showNotification("Incorrect data","All fields must be completed.",Notification.TYPE_ERROR_MESSAGE);
				}
				int age = 0;
				try {
					age = Integer.parseInt(ageField.getValue());		
				}
				catch (Exception e){
					if (argOk) { //otherwise an error had already be done before
						argOk = false;
						showNotification("Incorrect data","Age must be an integer.",Notification.TYPE_ERROR_MESSAGE);
					}
				}
				
				if(argOk){
					Student s = new Student();
					s.setName(name);
					s.setAddress(address);
					s.setAge(age);
					try {
//						studentEjb.addStudent(s);
						removeWindow(window);
						showNotification("Student Created");
					}
					catch (Exception e) {
						showNotification("Error creating student", Notification.TYPE_ERROR_MESSAGE);
					}		
				}	
			}
		});
		layout.addComponent(button);
		layout.setComponentAlignment(button, Alignment.TOP_CENTER);
	}
	
	private void findStudent(String arg) {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		String title = "";
		if (arg == "update") {
			title = "to update/delete student";
		}
		else if (arg == "addProject") {
			title = "owner of this new project";
		}
		else if (arg == "showProjects") {
			title = "to display its projects";
		}
		
		Window window = new Window("Find student " + title,layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
		
		TextField idField = new TextField("Student ID");
		idField.setInputPrompt("299792458");
		layout.addComponent(idField);
		
		Button button = new Button("Find it");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {		
				try {
					long id = Long.parseLong(idField.getValue());
//					Student student = studentEjb.findStudentById(id);
					// TO DELETE --------------------------
					Student student = new Student();
					student.setName("bob");
					student.setAddress("12 rue paul");
					student.setAge(42);
					student.setStudentID(1024l);
					// ------------------------------------ 
					if (student != null) {
						removeWindow(window);
						if (arg == "update") {
							updateStudent(student);
						}
						else if (arg == "addProject") {
							addProject(student);
						}
						else if (arg == "showProjects") {
							showProjects(student);
						}
					}
					else {
						showNotification("Student not found",Notification.TYPE_ERROR_MESSAGE);
					}
				}
				catch (Exception e){
					showNotification("Incorrect data","ID must be an integer.",Notification.TYPE_ERROR_MESSAGE);

				}			
			}
		});
		layout.addComponent(button);
		layout.setComponentAlignment(button, Alignment.TOP_CENTER);
	}
	
	private void updateStudent(Student student) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		Window window = new Window("Update/Delete this student",layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
			
		TextField nameField = new TextField("Name");
		nameField.setValue(student.getName());		
		layout.addComponent(nameField);
		
		TextField addressField = new TextField("Address");
		addressField.setValue(student.getAddress());
		layout.addComponent(addressField);
		
		TextField ageField = new TextField("Age");
		ageField.setValue("" + student.getAge());
		layout.addComponent(ageField);
		
		Button addProjectButton = new Button("Add Project");
		addProjectButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				addProject(student);
			}
		});
		layout.addComponent(addProjectButton);
		layout.setComponentAlignment(addProjectButton, Alignment.TOP_CENTER);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		
		Button updateButton = new Button("Update");
		updateButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				boolean argOk = true;
				
				String name = nameField.getValue();
				String address = addressField.getValue();
				if(name == "" || address == "") {
					argOk = false;
					showNotification("Incorrect data","All fields must be completed.",Notification.TYPE_ERROR_MESSAGE);
				}
				int age = 0;
				try {
					age = Integer.parseInt(ageField.getValue());	
				}
				catch (Exception e){
					if (argOk) { //otherwise an error had already be done before
						argOk = false;
						showNotification("Incorrect data","Age must be an integer.",Notification.TYPE_ERROR_MESSAGE);
					}
				}
				
				if(argOk){
					student.setName(name);
					student.setAddress(address);
					student.setAge(age);
					try {
//						studentEjb.updateStudent(student);
						removeWindow(window);
						showNotification("Student Updated");
					}
					catch (Exception e) {
						showNotification("Error updating student", Notification.TYPE_ERROR_MESSAGE);
					}		
				}	
			}
		});
		buttonLayout.addComponent(updateButton);
		
		Button deleteButton = new Button("Delete");
		deleteButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
//					studentEjb.deleteStudent(student);
					removeWindow(window);
					showNotification("Student Deleted");
				}
				catch (Exception e) {
					showNotification("Error deleting student", Notification.TYPE_ERROR_MESSAGE);
				}	
			}
		});
		buttonLayout.addComponent(deleteButton);
		
		layout.addComponent(buttonLayout);
	}

	private void showProjects(Student student) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		Window window = new Window("Student projects",layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
		
//		List<Project> data = projectEjb.findProjectsFromStudent(student.getStudentId());
		// TO DELETE --------------------------
		List<Project> data = new ArrayList<Project>();
		Project lol = new Project();
		lol.setTitle("Dat Big Project");
		lol.setOwnerID(1024l);
		lol.setProjectID(404l);
		data.add(lol);
		// ------------------------------------
		
		Table table = new Table("Projects of " + student.getStudentID() + "(" + student.getName() + ")");
		BeanItemContainer<Project> dSource = new BeanItemContainer<Project>(Project.class);
		dSource.addAll(data);
		table.setContainerDataSource(dSource);
		//Formatting
		table.setWidth("100%");
		table.setSelectable(true);
		table.setVisibleColumns( new Object[] {"projectID", "title"} );
		table.setColumnHeaders(new String[] {"Project ID", "Title"});
		layout.addComponent(table);
	}
	
	// PROJECTS -----------------------------
	
	private Layout projectLayout() {
			
			VerticalLayout layout = new VerticalLayout();
			layout.setMargin(true);
			
			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setMargin(true);
			toolBar.setSpacing(true);
			
			TextField filter = new TextField();
			filter.setInputPrompt("Filter Projects");
			toolBar.addComponent(filter);
			
			Button add = new Button("Add Project");
			add.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					findStudent("addProject");				
				}
			});
			toolBar.addComponent(add);
			
			Button up = new Button("Update/Delete Project");
			up.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					findProject();				
				}
			});
			toolBar.addComponent(up);
			
			layout.addComponent(toolBar);
			
//			List<Project> data = studentEjb.findProjects();
			// TO DELETE --------------------------
			List<Project> data = new ArrayList<Project>();
			Project lol = new Project();
			lol.setTitle("Dat Big Project");
			lol.setOwnerID(1024l);
			lol.setProjectID(404l);
			data.add(lol);
			// ------------------------------------
					
			Table table = new Table("Projects");
			BeanItemContainer<Project> dSource = new BeanItemContainer<Project>(Project.class);
			table.setContainerDataSource(dSource);
			dSource.addAll(data);
			//Formatting
			table.setWidth("100%");
			table.setSelectable(true);
			table.setVisibleColumns( new Object[] {"projectID", "title", "ownerID"} );
			table.setColumnHeaders(new String[] {"Project ID", "Title", "Owner ID"});
			layout.addComponent(table);
			
			//Filters
			filter.addTextChangeListener(new TextChangeListener() {
			    Filter filter = null;
	
			    public void textChange(TextChangeEvent event) {
			        Filterable f = (Filterable) dSource;
			        if (filter != null) {
			            f.removeContainerFilter(filter);
			        }
			        filter = new Or(new SimpleStringFilter("projectID", event.getText(), true, false),
			        				new SimpleStringFilter("title", event.getText(), true, false),
			        				new SimpleStringFilter("ownerID", event.getText(), true, false)
			        		);
			        f.addContainerFilter(filter);
			    }
			});
			
			return layout;
		}
	
	private void addProject(Student student) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		Window window = new Window("Add a new project",layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
		
		Label studName = new Label("Create a project for : " + student.getStudentID() + "(" + student.getName() + ")");
		
		TextField titleField = new TextField("Title");
		titleField.setInputPrompt("Dat awesome project");		
		layout.addComponent(titleField);
		
		Button button = new Button("Add this !");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				String title = titleField.getValue();
				if(title == "") {
					showNotification("Incorrect data","Projects must have titles.",Notification.TYPE_ERROR_MESSAGE);
				}
				else{
					Project p = new Project();
					p.setTitle(title);
					p.setOwnerID(student.getStudentID());
					try {
//						projectEjb.addProject(p);
						removeWindow(window);
						showNotification("Project Created");
					}
					catch (Exception e) {
						showNotification("Error creating project", Notification.TYPE_ERROR_MESSAGE);
					}		
				}	
			}
		});
		layout.addComponent(button);
		layout.setComponentAlignment(button, Alignment.TOP_CENTER);
	}

	private void findProject() {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		Window window = new Window("Find project to update/delete",layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
		
		TextField idField = new TextField("Project ID");
		idField.setInputPrompt("299792458");
		layout.addComponent(idField);
		
		Button button = new Button("Find it");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				try {
					long id = Long.parseLong(idField.getValue());
//					Project project = projectEjb.findProjectById(id);
					// TO DELETE --------------------------
					Project project = new Project();
					project.setTitle("Dat awesome project");
					project.setProjectID(404l);
					// ------------------------------------ 
					if (project != null) {
						removeWindow(window);
						updateProject(project);
					}
					else {
						showNotification("Project not found",Notification.TYPE_ERROR_MESSAGE);
					}
				}
				catch (Exception e){
					showNotification("Incorrect data","ID must be an integer.",Notification.TYPE_ERROR_MESSAGE);

				}			
			}
		});
		layout.addComponent(button);
		layout.setComponentAlignment(button, Alignment.TOP_CENTER);
	}
	
	private void updateProject(Project project) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		Window window = new Window("Update/Delete this project",layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
			
		TextField titleField = new TextField("Title");
		titleField.setValue(project.getTitle());		
		layout.addComponent(titleField);
		
		TextField ownerIDField = new TextField("OwnerID");
		ownerIDField.setValue("" + project.getOwnerID());
		layout.addComponent(ownerIDField);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		
		Button updateButton = new Button("Update");
		updateButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				boolean argOk = true;
				
				String title = titleField.getValue();
				if(title == "") {
					argOk = false;
					showNotification("Incorrect data","All fields must be completed.",Notification.TYPE_ERROR_MESSAGE);
				}
				long ownerID = 0;
				try {
					ownerID = Long.parseLong(ownerIDField.getValue());
				}
				catch (Exception e){
					if (argOk) { //otherwise an error had already be done before
						argOk = false;
						showNotification("Incorrect data","OwnerID must be an integer.",Notification.TYPE_ERROR_MESSAGE);
					}
				}
				
//				Student owner = studentEjb.findStudentById(ownerID);
				// TO DELETE --------------------------
				Student owner = new Student();
				owner.setName("bob");
				owner.setAddress("12 rue paul");
				owner.setAge(42);
				owner.setStudentID(1024l);
				// ------------------------------------ 
				if (owner == null) {
					argOk = false;
					showNotification("Owner student not found",Notification.TYPE_ERROR_MESSAGE);
				}
				
				if(argOk){
					project.setTitle(title);
					project.setOwnerID(ownerID);
					try {
//						projectEjb.updateProject(project);
						removeWindow(window);
						showNotification("Project Updated");
					}
					catch (Exception e) {
						showNotification("Error updating project", Notification.TYPE_ERROR_MESSAGE);
					}		
				}	
			}
		});
		buttonLayout.addComponent(updateButton);
		
		Button deleteButton = new Button("Delete");
		deleteButton.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
//					projectEjb.deleteProject(project);
					removeWindow(window);
					showNotification("Project Deleted");
				}
				catch (Exception e) {
					showNotification("Error deleting project", Notification.TYPE_ERROR_MESSAGE);
				}	
			}
		});
		buttonLayout.addComponent(deleteButton);
		
		layout.addComponent(buttonLayout);
	}

}