package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Project;
import com.bezkoder.springjwt.repository.ProjectRepository;
import com.bezkoder.springjwt.exceptions.ProjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/projects")
    Project newProject(@RequestBody Project newProject) {
        return projectRepository.save(newProject);
    }

    @GetMapping("/projects")
    List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/projects/{id}")
    Project getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFound(id));
    }

    @PutMapping("/projects/{id}")
    Project updateProject(@RequestBody Project newProject, @PathVariable Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(newProject.getName());
                    project.setDescription(newProject.getDescription());
                    project.setImageUrl(newProject.getImageUrl());
                    return projectRepository.save(project);
                }).orElseThrow(() -> new ProjectNotFound(id));
    }

    @DeleteMapping("/projects/{id}")
    String deleteProject(@PathVariable Long id){
        if(!projectRepository.existsById(id)){
            throw new ProjectNotFound(id);
        }
        projectRepository.deleteById(id);
        return  "Project with id "+id+" has been deleted success.";
    }



}
