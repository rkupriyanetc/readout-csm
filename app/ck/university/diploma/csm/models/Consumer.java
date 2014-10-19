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
}
