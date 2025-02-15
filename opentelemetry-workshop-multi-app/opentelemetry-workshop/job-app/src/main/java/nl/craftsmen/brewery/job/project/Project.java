package nl.craftsmen.brewery.job.project;

import nl.craftsmen.brewery.job.skill.Skill;

import java.util.List;

public class Project {

    private String name;

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
