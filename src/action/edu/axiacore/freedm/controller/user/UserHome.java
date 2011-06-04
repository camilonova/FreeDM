package edu.axiacore.freedm.controller.user;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import edu.axiacore.freedm.entity.User;

@Name("userHome")
public class UserHome extends EntityHome<User> {

	private User user;

	public void setUserId(Long id) {
		setId(id);
	}

	public Long getUserId() {
		return (Long) getId();
	}

	@Override
	protected User createInstance() {
		user = new User();
		return user;
	}

	public void wire() {
	}

	public boolean isWired() {
		return true;
	}

	public User getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
