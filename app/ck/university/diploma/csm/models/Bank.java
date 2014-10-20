package ck.university.diploma.csm.models;

import javax.persistence.Column;

public class Bank extends Identifier {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * bank.oshchad.name
	 */
	@Column( length = 70 )
	private String						name;
	
	/**
	 * bank.oshchad.mfo
	 */
	private int								mfo;
	
	public String getName() {
		return name;
	}
	
	public void setName( final String name ) {
		this.name = name;
	}
	
	public int getMFO() {
		return mfo;
	}
	
	public void setMFO( final int mfo ) {
		this.mfo = mfo;
	}
}
