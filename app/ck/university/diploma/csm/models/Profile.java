package ck.university.diploma.csm.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "profiles" )
public class Profile extends Identifier {
	
	private static final long	serialVersionUID	= 1L;
}
