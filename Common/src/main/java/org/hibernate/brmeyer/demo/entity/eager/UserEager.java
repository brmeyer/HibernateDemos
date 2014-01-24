package org.hibernate.brmeyer.demo.entity.eager;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.brmeyer.demo.entity.Comment;
import org.hibernate.brmeyer.demo.entity.Community;
import org.hibernate.brmeyer.demo.entity.Project;
import org.hibernate.brmeyer.demo.entity.Skill;
import org.hibernate.brmeyer.demo.entity.Tool;
import org.hibernate.brmeyer.demo.entity.User;

@Entity
public class UserEager extends User {
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Community> communityMemberships;
	
	@OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
	private List<Community> communitiesCreated;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Skill> skills;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Tool> tools;
	
	@OneToMany(mappedBy = "submitter", fetch = FetchType.EAGER)
	private List<Project> projectsSubmitted;
	
	@ManyToMany(mappedBy = "volunteers", fetch = FetchType.EAGER)
	private List<Project> projectsVolunteered;
	
	@OneToMany(mappedBy = "submitter", fetch = FetchType.EAGER)
	private List<Comment> comments;
	
	@Override
	public List<Community> getCommunityMemberships() {
		return communityMemberships;
	}
	
	@Override
	public void setCommunityMemberships(List<Community> communityMemberships) {
		this.communityMemberships = communityMemberships;
	}
	
	@Override
	public List<Community> getCommunitiesCreated() {
		return communitiesCreated;
	}
	
	@Override
	public void setCommunitiesCreated(List<Community> communitiesCreated) {
		this.communitiesCreated = communitiesCreated;
	}
	
	@Override
	public List<Skill> getSkills() {
		return skills;
	}
	
	@Override
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	@Override
	public List<Tool> getTools() {
		return tools;
	}
	
	@Override
	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}
	
	@Override
	public List<Project> getProjectsSubmitted() {
		return projectsSubmitted;
	}
	
	@Override
	public void setProjectsSubmitted(List<Project> projectsSubmitted) {
		this.projectsSubmitted = projectsSubmitted;
	}
	
	@Override
	public List<Project> getProjectsVolunteered() {
		return projectsVolunteered;
	}
	
	@Override
	public void setProjectsVolunteered(List<Project> projectsVolunteered) {
		this.projectsVolunteered = projectsVolunteered;
	}
	
	@Override
	public List<Comment> getComments() {
		return comments;
	}
	
	@Override
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
