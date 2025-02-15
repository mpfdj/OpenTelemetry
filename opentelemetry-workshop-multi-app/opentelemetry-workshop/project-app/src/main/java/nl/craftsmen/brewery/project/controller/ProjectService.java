package nl.craftsmen.brewery.project.controller;

import nl.craftsmen.brewery.project.model.Project;
import nl.craftsmen.brewery.project.model.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final SkillRepository skillRepository;

    public ProjectService(ProjectRepository projectRepository, SkillRepository skillRepository) {
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    @Transactional(readOnly = true)
    public List<Project> findAll() {
        LOGGER.debug("find all projects");
        return projectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Project> findProjectByName(String name) {
        LOGGER.debug("find project by name {}", name);
        return projectRepository.findProjectByNameIgnoreCase(name);
    }

    @Transactional
    public Project newProject(Project project) {
        LOGGER.debug("add new project '{}'", project.getName());
        List<Skill> skills = project.getSkills().stream()
                .map(s -> skillRepository.findByNameIgnoreCase(s.getName()).orElse(null))
                .filter(Objects::nonNull)
                .toList();
        project.setSkills(skills);
        return projectRepository.save(project);
    }
}
