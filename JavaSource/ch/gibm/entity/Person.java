package ch.gibm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Person.findPersonByIdWithLanguages", query = "select p from Person p left join fetch p.languages where p.id = :personId")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FIND_PERSON_BY_ID_WITH_LANGUAGES = "Person.findPersonByIdWithLanguages";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	// added Age on 20.03.2018
	private int age;
	
	@ManyToOne
	@JoinColumn(name = "role", foreignKey = @javax.persistence.ForeignKey(name = "role_fk"))
	private Role role;

	@ManyToMany
	private List<Language> languages;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Person) {
			Person person = (Person) obj;
			return person.getId() == id;
		}

		return false;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public Role getRole() {
		return this.role;
	}
}