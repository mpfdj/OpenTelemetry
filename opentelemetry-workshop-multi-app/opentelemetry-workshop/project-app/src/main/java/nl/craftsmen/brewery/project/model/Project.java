package nl.craftsmen.brewery.project.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "project")
public class Project extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany()
    @JoinTable(
            name = "project_skills",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private List<Skill> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
