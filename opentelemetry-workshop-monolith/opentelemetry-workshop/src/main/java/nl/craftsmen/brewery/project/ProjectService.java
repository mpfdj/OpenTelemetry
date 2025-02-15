package nl.craftsmen.brewery.project;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import nl.craftsmen.brewery.job.JobController;
import nl.craftsmen.brewery.model.Project;
import nl.craftsmen.brewery.model.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return projectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Project> findProjectByName(String name) {
        LOGGER.debug("find project by name '{}", name);
        return projectRepository.findProjectByName(name);
    }

    @Transactional
    public Project newProject(Project project) {
        List<Skill> skills = project.getSkills().stream()
                .map(s -> skillRepository.findByNameIgnoreCase(s.getName()).orElse(null))
                .filter(Objects::nonNull)
                .toList();
        project.setSkills(skills);
        return projectRepository.save(project);
    }
}
