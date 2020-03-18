package ch.hearc.dfts.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

	public Task(long id, String name, String description, boolean done) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.done = done;
	}
	
	public Task() 
	{
		super();
	}
	
	public Task(Task other)
	{
		this(other.id, other.name, other.description, other.done);
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
	
	
}
