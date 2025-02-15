package nl.craftsmen.brewery.job;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.craftsmen.brewery.company.CompanyService;
import nl.craftsmen.brewery.model.Developer;
import nl.craftsmen.brewery.model.Job;
import nl.craftsmen.brewery.model.Project;
import nl.craftsmen.brewery.project.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ProjectService projectService;
    private final CompanyService companyService;

    public JobService(JobRepository jobRepository, ProjectService projectService, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.projectService = projectService;
        this.companyService = companyService;
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public Optional<Job> findById(Integer id) {
        return jobRepository.findById(id);
    }

    public Optional<Job> findByProjectName(String name) {
        return jobRepository.findByProject_NameIgnoreCase(name);
    }

    public Job newJob(Job job) {
        Project project = projectService.findProjectByName(Objects.requireNonNull(job.getProject()).getName()).orElse(null);
        Developer developer = companyService.findDeveloperByName(
                Objects.requireNonNull(job.getDeveloper()).getLastName(),
                Objects.requireNonNull(job.getDeveloper()).getFirstName()).orElse(null);

        if (project != null && developer != null) {
            if (project.getSkills().stream().distinct().
                    anyMatch(s -> developer.getSkills().contains(s))) {
                Job newJob = new Job();
                newJob.setStartDate(job.getStartDate());
                newJob.setDescription(job.getDescription());
                newJob.setDeveloper(developer);
                newJob.setProject(project);
                return jobRepository.save(newJob);
            } else {
                throw new JobCreationException(project, developer);
            }
        }

        throw new JobCreationException(job.getProject(), job.getDeveloper());
    }
}
