package edu.axiacore.freedm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;

@Entity
@Table(name = "User", catalog = "freeDM")
public class User implements Serializable {

	private Long id;
	private String email;
	private String fullName;
	private String password;
	private String userName;
	private String role;

	public User() {
	}

	public User(String email, String fullName, String password,
			String userName, String role) {
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.userName = userName;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	@Email
	public void setEmail(String email) {
		this.email = email;
	}

	@Length(max = 50)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Length(max = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(max = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}