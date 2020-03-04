package ch.hearc.dfts.models;

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
}
