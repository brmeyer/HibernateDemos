/* 
 * Hibernate, Relational Persistence for Idiomatic Java
 * 
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.brmeyer.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;

/**
 * @author Brett Meyer
 */
public class EnversDemo {
	
	private final SessionFactory sessionFactory;
	
	public EnversDemo() {
		final Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( Project.class );
		sessionFactory = configuration.buildSessionFactory(
				new StandardServiceRegistryBuilder().build() );
	}
	
	public void insertData() {
		final Session s = openSession();
		s.getTransaction().begin();
		
		final Project project = new Project();
		project.setName( "fooName" );
		project.setType( "fooType" );
		s.persist( project );
		
		s.getTransaction().commit();
		s.clear();
		
		for (int i = 0; i < 5; i++) {
			s.getTransaction().begin();
			
			project.setName( "fooName" + i );
			s.update( project );
			
			s.getTransaction().commit();
			s.clear();
		}
		
		s.close();
	}

	public List getProjectRevisions() {
		final Session s = openSession();
		final AuditReader ar = AuditReaderFactory.get( s );
		return ar.createQuery()
			    .forRevisionsOfEntity(Project.class, false, true)
			    .getResultList();
	}

	public List getProjectRevisions(String property) {
		final Session s = openSession();
		final AuditReader ar = AuditReaderFactory.get( s );
		return ar.createQuery()
			    .forRevisionsOfEntity(Project.class, false, true)
			    .add(AuditEntity.property(property).hasChanged())
			    .getResultList();
	}

	public List getRevisionProjects(int revisionNumber) {
		final Session s = openSession();
		final AuditReader ar = AuditReaderFactory.get( s );
		return ar.createQuery()
			    .forEntitiesAtRevision(Project.class, revisionNumber)
			    .getResultList();
	}
	
	public void printRevisions(List<Object[]> revisions) {
		for (Object[] revision : revisions) {
			final Project project = (Project) revision[0];
			final DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) revision[1];
			final RevisionType revisionType = (RevisionType) revision[2];
			
			System.out.println();
			System.out.println(project.toString());
			System.out.println(revisionEntity.toString());
			System.out.println("REVISION TYPE: " + revisionType.name());
			System.out.println();
		}
	}
	
	private Session openSession() {
		return sessionFactory.openSession();
	}

	public static void main(String[] args) {
		final EnversDemo demo = new EnversDemo();
		demo.insertData();
		
		System.out.println("***** PROJECT REVISIONS *****");
		List results = demo.getProjectRevisions();
		demo.printRevisions( results );
		
		System.out.println("***** PROJECT REVISIONS THAT UPDATED #NAME *****");
		results = demo.getProjectRevisions( "name" );
		demo.printRevisions( results );
		
		// TODO: Not working!  Still prints all revisions, even though only #name was updated!
//		System.out.println("***** PROJECT REVISIONS THAT UPDATED #TYPE *****");
//		results = demo.getProjectRevisions( "type" );
//		demo.printRevisions( results );
		
		System.out.println("***** PROJECT IN REVISION #3 *****");
		Project project = (Project) demo.getRevisionProjects( 3 ).get( 0 );
		System.out.println(project.toString());
	}
}
