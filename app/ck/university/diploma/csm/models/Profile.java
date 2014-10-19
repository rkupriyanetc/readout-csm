package ck.university.diploma.csm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "profiles" )
public class Profile extends Identifier {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Коротка назва організації
	 */
	@Column( length = 50 )
	private String						name;
	
	/**
	 * Повна назва організації
	 */
	@Column( length = 200 )
	private String						fullName;
	
	/**
	 * Розрахунковий рахунок
	 */
	@Column( length = 15 )
	private String						account;
	
	/**
	 * МФО
	 */
	private int								mfo;
	
	/**
	 * ЄДРПОУ
	 */
	private int								edrpou;
}
