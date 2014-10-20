package ck.university.diploma.csm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "consumers" )
public class Consumer extends Identifier {
	
	private static final long											serialVersionUID	= 1L;
	
	@Column( length = 60 )
	private String																firstLastName;
	
	private User																	userId;
	
	public static final Finder< Long, Consumer >	find							= new Finder< Long, Consumer >( Long.class, Consumer.class );
	
	public String getFirstLastName() {
		return firstLastName;
	}
	
	public void setFirstLastName( final String firstLastName ) {
		this.firstLastName = firstLastName;
	}
	
	public Consumer() {}
	
	public static Consumer findById( final long id ) {
		return find.where().eq( "id", id ).findUnique();
	}
}