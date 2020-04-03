package ch.hearc.dfts.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_tasks")
public class Task {
	
	@GeneratedValue
	@Id
	private long id;
	
	private String name;
	private String description;
	
	private boolean done;

    @ManyToMany(mappedBy = "tasks", cascade = {CascadeType.ALL})
    private Set<User> users;

	public Task(long id, String name, String description, boolean done, Set<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.done = done;
		this.users = users;
	}
	
	public Task() 
	{
		super();
		
		this.done = false;
	}
	
	public Task(Task other)
	{
		this(other.id, other.name, other.description, other.done, other.users);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}
