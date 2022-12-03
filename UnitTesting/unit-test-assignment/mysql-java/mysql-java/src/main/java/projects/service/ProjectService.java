package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;


public class ProjectService {
	private ProjectDao projectDao = new ProjectDao();
	
	// code to ask DAO class to insert a project row.
	public Project addProject(Project project) {
		return projectDao.insertProject(project);
	}
	
	// Week 10
//Method calls for the project DAO to retrieve all project rows without any details and returns a list of the projects records.
	public List<Project> fetchAllProjects() {
		return projectDao.fetchAllProjects();
	}

	//Method calls for project details, and if the project ID is invalid will throw an exception.
	
	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException(
				"Project with project ID=" + projectId + "does not exist."));
	}
	
	//Week 11
	// Project object is passed as a parameter. The DAO method returns a boolean that indicates whether the UPDATE operation was successful. 
	// If false, throws a Db Exception that project does not exist. 
	
	public void modifyProjectDetails(Project project) {
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("Project with ID=" + project.getProjectId() + " does not exist.");
		}
		
	}

	public void deleteProject(Integer projectId) {
		if(!projectDao.deleteProject(projectId)) {
			throw new DbException("Project with ID=" + projectId + "does not excist.");
		}
		
	}
	
	
	
	
	
	
	
	
}
