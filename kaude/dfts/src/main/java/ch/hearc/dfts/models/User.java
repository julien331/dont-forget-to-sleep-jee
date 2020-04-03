package ch.hearc.dfts.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "tbl_users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@Column(unique = true, length = 50)
	private String name;
	
	@NotNull
	@NotEmpty
	private String password;

	@Column(unique = true, length = 100)
	@Email(message = "Entrer une adresse courriel valide")
	private String email;

	@ManyToMany(cascade = { CascadeType.ALL })
	private Set<Role> roles;

	@ManyToMany(cascade = { CascadeType.ALL })
	private Set<Task> tasks;

	public User(Long id, String name, String password, String email, Set<Role> roles, Set<Task> tasks) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.roles = roles;
		this.tasks = tasks;
	}

	public User(User other) {
		this(other.id, other.name, other.password, other.email, other.roles, other.tasks);
	}

	public User() {
		super();
		this.name = "toto";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nomUtilisateur) {
		this.name = nomUtilisateur;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String motDePasse) {
		this.password = motDePasse;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", confirmationToken=, roles=" + roles + ", tasks=" + tasks + "]";
	}

}
