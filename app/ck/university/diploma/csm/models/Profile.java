package ck.university.diploma.csm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "profiles" )
public class Profile extends Identifier {
	
	private static final long										serialVersionUID	= 1L;
	
	/**
	 * profile.official.name
	 * To cheque jur. consumers
	 */
	@Column( length = 50 )
	private String															topName;
	
	/**
	 * profile.home.name
	 * To cheque jur. consumers
	 */
	@Column( length = 50 )
	private String															name;
	
	/**
	 * profile.home.fullName
	 */
	@Column( length = 200 )
	private String															fullName;
	
	/**
	 * profile.home.account
	 * To cheque individual and jur. consumers
	 */
	@Column( length = 15 )
	private String															account;
	
	/**
	 * profile.home.edrpou
	 * To cheque individual and jur. consumers
	 */
	private int																	edrpou;
	
	/**
	 * profile.home.taxNumber
	 * To cheque jur. consumers
	 */
	@Column( length = 15 )
	private String															taxNumber;
	
	/**
	 * profile.home.certificateNumber
	 * To cheque jur. consumers
	 */
	private int																	certificateNumber;
	
	/**
	 * profile.home.branchId: 14
	 */
	private byte																branchId;
	
	public static final Finder< Long, Profile >	find							= new Finder< Long, Profile >( Long.class, Profile.class );
	
	public String getTopName() {
		return topName;
	}
	
	public void setTopName( final String topName ) {
		this.topName = topName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( final String name ) {
		this.name = name;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName( final String fullName ) {
		this.fullName = fullName;
	}
	
	public int getEDRPOU() {
		return edrpou;
	}
	
	public void setEDRPOU( final int edrpou ) {
		this.edrpou = edrpou;
	}
	
	public String getTaxNumber() {
		return taxNumber;
	}
	
	public void setTaxNumber( final String taxNumber ) {
		this.taxNumber = taxNumber;
	}
	
	public int getCertificateNumber() {
		return certificateNumber;
	}
	
	public void setCertificateNumber( final int certificateNumber ) {
		this.certificateNumber = certificateNumber;
	}
	
	public byte getBranchId() {
		return branchId;
	}
	
	public void setBranchId( final byte branchId ) {
		this.branchId = branchId;
	}
	
	public Profile() {}
	
	public static Profile findById( final long id ) {
		return find.where().eq( "id", id ).findUnique();
	}
}
