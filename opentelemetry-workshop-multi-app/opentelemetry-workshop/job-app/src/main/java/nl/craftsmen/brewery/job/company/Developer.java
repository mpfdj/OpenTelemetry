package nl.craftsmen.brewery.job.company;

import nl.craftsmen.brewery.job.skill.Skill;

import java.util.List;

public class Developer {

    private String firstName;

    private String lastName;

    private List<Skill> skills;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}