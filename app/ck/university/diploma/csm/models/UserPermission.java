package ck.university.diploma.csm.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import be.objectify.deadbolt.core.models.Permission;

/**
 * Initial version based on work by Steve Chaloner (steve@objectify.be) for
 * Deadbolt2
 */
@Entity
@Table( name = "permissions" )
public class UserPermission extends Identifier implements Permission {
	
	private static final long	serialVersionUID	= 1L;
	
	private String						value;
	
	@Override
	public String getValue() {
		return value;
	}
	
	public void setValue( final String value ) {
		this.value = value;
	}
	
	public static final Finder< Long, UserPermission >	find	= new Finder< Long, UserPermission >( Long.class,
																																UserPermission.class );
	
	public static UserPermission findByValue( String value ) {
		return find.where().eq( "value", value ).findUnique();
	}
}
