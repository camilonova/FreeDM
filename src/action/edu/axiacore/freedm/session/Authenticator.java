package edu.axiacore.freedm.session;

import java.text.DateFormat;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import edu.axiacore.freedm.entity.User;

@Name("authenticator")
public class Authenticator {
	
	@Logger
	Log log;

	@In
	Identity identity;

	@In
	EntityManager entityManager;
	
	private User user;
	
	public boolean authenticate() {
		log.info("authenticating #0", identity.getUsername());
		
		try {
			//TODO Revisar si funciona para evitar que entre desde dos lugares distintos el mismo usuario
			if (identity.isLoggedIn()) {
				return false;
			}
			
			Query query = entityManager.createQuery("from User where userName = :username and password = :password");
			query.setParameter("username", identity.getUsername());
			query.setParameter("password", identity.getPassword());
			
			user = (User) query.getSingleResult();			
			identity.addRole(user.getRole());
			log.info("authentication succeed for #0 as #1, at \"#2\" ", user.getFullName(), user.getRole(), DateFormat.getDateTimeInstance().format(System.currentTimeMillis()));
			return true;
		} catch (NoResultException ex) {
			log.error("authentication rejected for #0 at \"#1\" ", identity.getUsername(), DateFormat.getDateTimeInstance().format(System.currentTimeMillis()));
			return false;
		}
	}

}
