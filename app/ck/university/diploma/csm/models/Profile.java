package ck.university.diploma.csm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "profiles" )
public class Profile extends Identifier {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * ������� ����� ����������
	 */
	@Column( length = 50 )
	private String						name;
	
	/**
	 * ����� ����� ����������
	 */
	@Column( length = 200 )
	private String						fullName;
	
	/**
	 * ������������� �������
	 */
	@Column( length = 15 )
	private String						account;
	
	/**
	 * ���
	 */
	private int								mfo;
	
	/**
	 * ������
	 */
	private int								edrpou;
}
