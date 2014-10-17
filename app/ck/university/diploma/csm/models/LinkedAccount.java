package ck.university.diploma.csm.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.feth.play.module.pa.user.AuthUser;

@Entity
@Table( name = "linkeds" )
public class LinkedAccount extends Identifier {
	
	private static final long													serialVersionUID	= 1L;
	
	@ManyToOne
	private User																			user;
	
	private String																		providerUserId;
	
	private String																		providerKey;
	
	public static final Finder< Long, LinkedAccount >	find							= new Finder< Long, LinkedAccount >( Long.class,
																																					LinkedAccount.class );
	
	public User getUser() {
		return user;
	}
	
	public void setUser( final User user ) {
		this.user = user;
	}
	
	public String getProviderUserId() {
		return providerUserId;
	}
	
	public void setProviderUserId( final String providerUserId ) {
		this.providerUserId = providerUserId;
	}
	
	public String getProviderKey() {
		return providerKey;
	}
	
	public void setProviderKey( final String providerKey ) {
		this.providerKey = providerKey;
	}
	
	public static LinkedAccount findByProviderKey( final User user, final String key ) {
		return find.where().eq( "user", user ).eq( "providerKey", key ).findUnique();
	}
	
	public static LinkedAccount create( final AuthUser authUser ) {
		final LinkedAccount ret = new LinkedAccount();
		ret.update( authUser );
		return ret;
	}
	
	public void update( final AuthUser authUser ) {
		this.providerKey = authUser.getProvider();
		this.providerUserId = authUser.getId();
	}
	
	public static LinkedAccount create( final LinkedAccount acc ) {
		final LinkedAccount ret = new LinkedAccount();
		ret.providerKey = acc.providerKey;
		ret.providerUserId = acc.providerUserId;
		return ret;
	}
}