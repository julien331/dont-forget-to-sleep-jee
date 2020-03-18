package ch.hearc.dfts.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.ALL})
    private Set<User> users;

	public Role(Long id, String nom, Set<User> users) {
		super();
		this.id = id;
		this.nom = nom;
		this.users = users;
	}

    public Role(Role other)
    {
    	this(other.id, other.nom, other.users);
    }
    
    public Role()
    {
    	this(0l, "Sous-merde", null);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
