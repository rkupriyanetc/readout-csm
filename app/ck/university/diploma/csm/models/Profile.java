package ck.university.diploma.csm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "profiles" )
public class Profile extends Identifier {
	
	private static final long										serialVersionUID	= 1L;
	
	/**
	 * Верхня назва організації ПАТ "Черкасиобленерго"
	 * Йде в рахунок та Юр. споживачам
	 */
	@Column( length = 50 )
	private String															topName;
	
	/**
	 * Коротка назва організації Маньківський РЕМ
	 * Йде в рахунок та Юр. споживачам
	 */
	@Column( length = 50 )
	private String															name;
	
	/**
	 * Повна назва організації Маньківський район електричних мереж
	 */
	@Column( length = 200 )
	private String															fullName;
	
	/**
	 * Розрахунковий рахунок 26035860199956
	 * Йде в рахунок та Юр. споживачам
	 */
	@Column( length = 15 )
	private String															account;
	
	/**
	 * ЄДРПОУ 25204695
	 * Йде в рахунок та Юр. споживачам
	 */
	private int																	edrpou;
	
	/**
	 * Індивідуальний податковий № 228007323019
	 * Йде в рахунок Юр. споживачам
	 */
	@Column( length = 15 )
	private String															taxNumber;
	
	/**
	 * № свідоцтва 200007943 ( 32121400 )
	 * Йде в рахунок Юр. споживачам
	 */
	private int																	certificateNumber;
	
	/**
	 * Філія № 14
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
