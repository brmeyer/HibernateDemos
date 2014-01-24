package org.hibernate.brmeyer.demo.entity.eager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.brmeyer.demo.entity.Project;
import org.hibernate.brmeyer.demo.entity.User;

@Entity
public class ProjectEager extends Project {

	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> volunteers;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> imageUrls = new ArrayList<String>();

    @Override
	public List<User> getVolunteers() {
		return volunteers;
	}

    @Override
	public void setVolunteers(List<User> volunteers) {
		this.volunteers = volunteers;
	}

    @Override
	public List<String> getImageUrls() {
        return imageUrls;
    }

    @Override
	public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
	}
}
