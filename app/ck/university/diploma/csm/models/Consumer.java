package ck.university.diploma.csm.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "consumers" )
public class Consumer extends Identifier {
	
	private static final long	serialVersionUID	= 1L;
	
	private String						firstName;
	
	private String						name;
	
	private String						lastName;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName( final String firstName ) {
		this.firstName = firstName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( final String name ) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName( final String lastName ) {
		this.lastName = lastName;
	}
}
