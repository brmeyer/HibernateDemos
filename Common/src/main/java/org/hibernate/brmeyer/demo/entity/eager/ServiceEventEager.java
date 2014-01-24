package org.hibernate.brmeyer.demo.entity.eager;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.brmeyer.demo.entity.Project;
import org.hibernate.brmeyer.demo.entity.ServiceEvent;

@Entity
public class ServiceEventEager extends ServiceEvent {

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Project> projects;

	@Override
	public List<Project> getProjects() {
		return projects;
	}

	@Override
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}
