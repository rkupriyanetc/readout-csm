package ck.university.diploma.csm.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

@MappedSuperclass
public class Identifier extends Model {
	
	@Id
	@GeneratedValue
	private long	id;
	
	public long getId() {
		return id;
	}
}
