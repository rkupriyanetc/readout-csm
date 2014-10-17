package ck.university.diploma.csm.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.format.Formats;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.QueryIterator;

@Entity
@Table( name = "tokens" )
public class TokenAction extends Identifier {
	
	public enum TokenType {
		EMAIL_VERIFICATION, PASSWORD_RESET
	}
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * Verification time frame (until the user clicks on the link in the email)
	 * in seconds. Defaults to one week.
	 */
	private final static long	VERIFICATION_TIME	= 7 * 24 * 3600;
	
	@Column( unique = true )
	private String						token;
	
	@ManyToOne
	private User							targetUser;
	
	private TokenType					type;
	
	@Formats.DateTime( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date							created;
	
	@Formats.DateTime( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date							expires;
	
	public User getUser() {
		return targetUser;
	}
	
	public void setUser( final User user ) {
		this.targetUser = user;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken( final String token ) {
		this.token = token;
	}
	
	public TokenType getTokenType() {
		return type;
	}
	
	public void setTokenType( final TokenType type ) {
		this.type = type;
	}
	
	public Date getDateCreated() {
		return created;
	}
	
	public void setDateCreated( final Date created ) {
		this.created = created;
	}
	
	public Date getDateExpires() {
		return expires;
	}
	
	public void setDateExpires( final Date expires ) {
		this.expires = expires;
	}
	
	public static final Finder< Long, TokenAction >	find	= new Finder< Long, TokenAction >( Long.class, TokenAction.class );
	
	public static TokenAction findByToken( final String token, final TokenType type ) {
		return find.where().eq( "token", token ).eq( "type", type.name() ).findUnique();
	}
	
	public static void deleteByUser( final User u, final TokenType type ) {
		QueryIterator< TokenAction > iterator = find.where().eq( "targetUser.id", u.getId() ).eq( "type", type.name() ).findIterate();
		Ebean.delete( iterator );
		iterator.close();
	}
	
	public boolean isValid() {
		return this.expires.after( new Date() );
	}
	
	public static TokenAction create( final TokenType type, final String token, final User targetUser ) {
		final TokenAction ua = new TokenAction();
		ua.targetUser = targetUser;
		ua.token = token;
		ua.type = type;
		final Date created = new Date();
		ua.created = created;
		ua.expires = new Date( created.getTime() + VERIFICATION_TIME * 1000 );
		ua.save();
		return ua;
	}
}
