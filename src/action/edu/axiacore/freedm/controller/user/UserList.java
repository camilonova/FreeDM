package edu.axiacore.freedm.controller.user;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import edu.axiacore.freedm.entity.User;

import java.util.List;
import java.util.Arrays;

@Name("userList")
public class UserList extends EntityQuery {

	private static final String[] RESTRICTIONS = {
			"lower(user.email) like concat(lower(#{userList.user.email}),'%')",
			"lower(user.fullName) like concat(lower(#{userList.user.fullName}),'%')",
			"lower(user.password) like concat(lower(#{userList.user.password}),'%')",
			"lower(user.userName) like concat(lower(#{userList.user.userName}),'%')",
			"lower(user.role) like concat(lower(#{userList.user.role}),'%')", };

	private User user = new User();

	@Override
	public String getEjbql() {
		return "select user from User user";
	}

	@Override
	public Integer getMaxResults() {
		return 25;
	}

	public User getUser() {
		return user;
	}

	@Override
	public List<String> getRestrictions() {
		return Arrays.asList(RESTRICTIONS);
	}

}
