package com.matteo.studentapp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

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
	
//	StudentEJB ejb = new StudentEJB();

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
				findStudent();				
			}
		});
		toolBar.addComponent(up);
		
		/*
		Label advice = new Label("Click on a student to update/delete it.");
		toolBar.addComponent(advice);
		toolBar.setComponentAlignment(advice, Alignment.BOTTOM_LEFT);
		*/
		
		layout.addComponent(toolBar);
		
//		List<Student> data = ejb.findStudents();
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
		
		TextField idField = new TextField("ID");
		idField.setInputPrompt("299792458");
		layout.addComponent(idField);
		
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
				long id = 0;
				try {
					age = Integer.parseInt(ageField.getValue());
					id = Long.parseLong(idField.getValue());		
				}
				catch (Exception e){
					if (argOk) { //otherwise an error had already be done before
						argOk = false;
						showNotification("Incorrect data","Age and ID must be integers.",Notification.TYPE_ERROR_MESSAGE);
					}
				}
				
				if(argOk){
					Student s = new Student();
					s.setName(name);
					s.setAddress(address);
					s.setAge(age);
					s.setStudentID(id);
					try {
//						ejb.addStudent(s);
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
	
	private void findStudent() {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		Window window = new Window("Find student to update/delete",layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
		
		TextField idField = new TextField("ID");
		idField.setInputPrompt("299792458");
		layout.addComponent(idField);
		
		Button button = new Button("Find it");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				try {
					long id = Long.parseLong(idField.getValue());
					removeWindow(window);
					updateStudent(id);
				}
				catch (Exception e){
					showNotification("Incorrect data","ID must be an integer.",Notification.TYPE_ERROR_MESSAGE);

				}			
			}
		});
		layout.addComponent(button);
		layout.setComponentAlignment(button, Alignment.TOP_CENTER);
	}
	
	private void updateStudent(long id) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		Window window = new Window("Update/Delete this student",layout);
		window.setModal(true);
		window.setResizable(false);
		addWindow(window);
		
//		Student student = ejb.findStudentById(id);
		// TO DELETE --------------------------
		Student student = new Student();
		student.setName("bob");
		student.setAddress("12 rue paul");
		student.setAge(42);
		student.setStudentID(1024l);
		// ------------------------------------   
		
		TextField nameField = new TextField("Name");
		nameField.setValue(student.getName());		
		layout.addComponent(nameField);
		
		TextField addressField = new TextField("Address");
		addressField.setValue(student.getAddress());
		layout.addComponent(addressField);
		
		TextField ageField = new TextField("Age");
		ageField.setValue("" + student.getAge());
		layout.addComponent(ageField);
		
		TextField idField = new TextField("ID");
		idField.setValue("" + student.getStudentID());
		layout.addComponent(idField);
		
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
				long id = 0;
				try {
					age = Integer.parseInt(ageField.getValue());
					id = Long.parseLong(idField.getValue());		
				}
				catch (Exception e){
					if (argOk) { //otherwise an error had already be done before
						argOk = false;
						showNotification("Incorrect data","Age and ID must be integers.",Notification.TYPE_ERROR_MESSAGE);
					}
				}
				
				if(argOk){
					Student s = new Student();
					s.setName(name);
					s.setAddress(address);
					s.setAge(age);
					s.setStudentID(id);
					try {
//						ejb.updateStudent(s);
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
//					ejb.deleteStudent(student);
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

	// PROJTECS -----------------------------
}