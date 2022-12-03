package projects;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

/* Menu driven application that will accept user input from the console. It allows to perform CRUD operations 
 * on the project tables. 
 */
//1st stage: Create a project
//2nd stage: Read a project


//
public class ProjectsApp {
	
	
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
	// @formatter:off
		private List<String> operations = List.of(
				"1) Add a project",
				"2) List projects",
				"3) Select a project",
				"4) Update project details",
				"5) Delete a project"
			); 			
	// @formatter:on
	
	
	
	public static void main(String[] args) {
		new ProjectsApp().processUserSelections();
		
	}

	
	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {
			try {
				int selection = getUserSelection();
				
				switch(selection) {
					case -1:
						done = exitMenu();
						break;
				
					case 1:
						createProject();
						break;	
						
					case 2:
						listProjects();
						break;
					case 3:
						selectProject();
						break;
					case 4:
						updateProjectDetails();
						break;
					case 5:
						deleteProject();
						break;
						
					default:
						System.out.println("\n" + selection + "is not a valid selection. Try again.");
					
				}	
			}
			
			catch(Exception e) {
				System.out.println("\nError: " + e + "Try again.");
				e.printStackTrace();
			}
		}
		
	}
	
    private void deleteProject() {
		listProjects();
		
		Integer projectId = getIntInput("Enter the ID of the project to delete");
		
		projectService.deleteProject(projectId);
		System.out.println("Project " + projectId + " was deleted succesfully.");
		
		if(Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
			curProject =null;
		}
		
	}


	// Week 11 
	//updates project details of a project on the list.
   @SuppressWarnings("unused")
private void updateProjectDetails() {
	   if(Objects.isNull(curProject)) {
		   System.out.println("\nPlease select a project.");
		   return;
	   }
	   
	   String projectName = getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
	   
	   BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
	   
	   BigDecimal actualHours = getDecimalInput("Enter the actual hours + [" + curProject.getActualHours() + "]");
	   
	   Integer difficulty = getIntInput("Enter the project difficulty (1-5) [" + curProject.getDifficulty() + "]");
	   
	   String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
	   
	   Project project = new Project();
	   
	   project.setProjectId(curProject.getProjectId());
	   project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
	   
	   project.setEstimatedHours(Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
	   
	   project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
	   project.setDifficulty(Objects.isNull(difficulty) ? curProject.getProjectName() : projectName);
	   project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
	   
	   projectService.modifyProjectDetails(project);
	   
	   curProject =projectService.fetchProjectById(curProject.getProjectId());
   }
	   
	   
	   
		
	


private void selectProject() {
		listProjects();
		Integer projectId = getIntInput("Enter a projectID to select a project");
		
		//to current project.
		curProject = null;
		
		curProject = projectService.fetchProjectById(projectId);
		
		
		
		
		
	}


private void listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		
		System.out.println("\nProjects:");
		
		projects.forEach(project -> System.out
				.println("  " + project.getProjectId() + ": " + project.getProjectName()));
		
	

		
	}


// code with user input for project row to implement project service.
	private void createProject() {
		String projectName = getStringInput("Enter the Project Name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimate hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}

	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt); 
		
		if(Objects.isNull(input)) {
			return null;
		}
		// code written to create an object that will set number of decimal places to 2.
		try {
			return new BigDecimal(input).setScale(2);
		}		
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
	}

	//Code to be called when to terminate and exit the application.
	
		private boolean exitMenu() {
			System.out.println("Exiting the menu.");
			return true;
		}
	
	
	// method to input menu selection depending on the integer.
	private int getUserSelection() {
	   printOperations();
	   
	   Integer input = getIntInput("Enter a menu selection");
		return Objects.isNull(input) ? -1 : input;
	}
	


//prints the prompt and converts value to appropriate type.
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt); 
		
		if(Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Integer.valueOf(input);
		}		
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
		
	}
	
//method that will determine the inputs value, if none returns null.
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
		
	}
	
		private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		
		
		operations.forEach(line -> System.out.println(" " + line));
		
		
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		}
		else {
			System.out.println("\nYou are working with project: " + curProject);
		}
	}
}