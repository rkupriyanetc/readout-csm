package ck.university.diploma.csm.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.format.Formats;
import play.data.validation.Constraints;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import ck.university.diploma.csm.models.TokenAction.TokenType;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;

/**
 * Initial version based on work by Steve Chaloner (steve@objectify.be) for
 * Deadbolt2
 */
@Entity
@Table( name = "users" )
public class User extends Identifier implements Subject {
	
	private static final long									serialVersionUID	= 1L;
	
	@Constraints.Email
	// if you make this unique, keep in mind that users *must* merge/link their
	// accounts then on signup with additional providers
	// @Column(unique = true)
	private String														email;
	
	private String														name;
	
	@Formats.DateTime( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date															lastLogin;
	
	private boolean														active;
	
	private boolean														emailValidated;
	
	@ManyToMany
	private List< SecurityRole >							roles							= new ArrayList< SecurityRole >( 0 );
	
	@OneToMany( cascade = CascadeType.ALL )
	private List< LinkedAccount >							linkedAccounts		= new ArrayList< LinkedAccount >( 0 );
	
	@ManyToMany
	private List< UserPermission >						permissions				= new ArrayList< UserPermission >( 0 );
	
	public static final Finder< Long, User >	find							= new Finder< Long, User >( Long.class, User.class );
	
	@Override
	public String getIdentifier() {
		return String.valueOf( getId() );
	}
	
	@Override
	public List< ? extends Role > getRoles() {
		return roles;
	}
	
	public List< LinkedAccount > getLinkedAccounts() {
		return linkedAccounts;
	}
	
	@Override
	public List< ? extends Permission > getPermissions() {
		return permissions;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail( final String email ) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( final String name ) {
		this.name = name;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin( final Date lastLogin ) {
		this.lastLogin = lastLogin;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isEmailValidated() {
		return emailValidated;
	}
	
	public static boolean existsByAuthUserIdentity( final AuthUserIdentity identity ) {
		final ExpressionList< User > exp;
		if ( identity instanceof UsernamePasswordAuthUser ) {
			exp = getUsernamePasswordAuthUserFind( ( UsernamePasswordAuthUser )identity );
		} else {
			exp = getAuthUserFind( identity );
		}
		return exp.findRowCount() > 0;
	}
	
	private static ExpressionList< User > getAuthUserFind( final AuthUserIdentity identity ) {
		return find.where().eq( "active", true ).eq( "linkedAccounts.providerUserId", identity.getId() )
				.eq( "linkedAccounts.providerKey", identity.getProvider() );
	}
	
	public static User findByAuthUserIdentity( final AuthUserIdentity identity ) {
		if ( identity == null ) {
			return null;
		}
		if ( identity instanceof UsernamePasswordAuthUser ) {
			return findByUsernamePasswordIdentity( ( UsernamePasswordAuthUser )identity );
		} else {
			return getAuthUserFind( identity ).findUnique();
		}
	}
	
	public static User findByUsernamePasswordIdentity( final UsernamePasswordAuthUser identity ) {
		return getUsernamePasswordAuthUserFind( identity ).findUnique();
	}
	
	private static ExpressionList< User > getUsernamePasswordAuthUserFind( final UsernamePasswordAuthUser identity ) {
		return getEmailUserFind( identity.getEmail() ).eq( "linkedAccounts.providerKey", identity.getProvider() );
	}
	
	public void merge( final User otherUser ) {
		for ( final LinkedAccount acc : otherUser.linkedAccounts ) {
			this.linkedAccounts.add( LinkedAccount.create( acc ) );
		}
		// do all other merging stuff here - like resources, etc.
		// deactivate the merged user that got added to this one
		otherUser.active = false;
		Ebean.save( Arrays.asList( new User[] { otherUser, this } ) );
	}
	
	public static User create( final AuthUser authUser ) {
		final User user = new User();
		user.roles.add( SecurityRole.USER_ROLE );
		// user.permissions = new ArrayList<UserPermission>();
		// user.permissions.add(UserPermission.findByValue("printers.edit"));
		user.active = true;
		user.lastLogin = new Date();
		user.linkedAccounts.add( LinkedAccount.create( authUser ) );
		if ( authUser instanceof EmailIdentity ) {
			final EmailIdentity identity = ( EmailIdentity )authUser;
			// Remember, even when getting them from FB & Co., emails should be
			// verified within the application as a security breach there might
			// break your security as well!
			user.email = identity.getEmail();
			user.emailValidated = false;
		}
		if ( authUser instanceof NameIdentity ) {
			final NameIdentity identity = ( NameIdentity )authUser;
			final String name = identity.getName();
			if ( name != null ) {
				user.name = name;
			}
		}
		/**
		 * if ( authUser instanceof FirstLastNameIdentity ) {
		 * final FirstLastNameIdentity identity = ( FirstLastNameIdentity )authUser;
		 * final String firstName = identity.getFirstName();
		 * final String lastName = identity.getLastName();
		 * if ( firstName != null ) {
		 * user.firstName = firstName;
		 * }
		 * if ( lastName != null ) {
		 * user.lastName = lastName;
		 * }
		 * }
		 */
		user.save();
		user.saveManyToManyAssociations( "roles" );
		// user.saveManyToManyAssociations("permissions");
		return user;
	}
	
	public static void merge( final AuthUser oldUser, final AuthUser newUser ) {
		User.findByAuthUserIdentity( oldUser ).merge( User.findByAuthUserIdentity( newUser ) );
	}
	
	public Set< String > getProviders() {
		final Set< String > providerKeys = new HashSet< String >( linkedAccounts.size() );
		for ( final LinkedAccount acc : linkedAccounts ) {
			providerKeys.add( acc.getProviderKey() );
		}
		return providerKeys;
	}
	
	public static void addLinkedAccount( final AuthUser oldUser, final AuthUser newUser ) {
		final User u = User.findByAuthUserIdentity( oldUser );
		u.linkedAccounts.add( LinkedAccount.create( newUser ) );
		u.save();
	}
	
	public static void setLastLoginDate( final AuthUser knownUser ) {
		final User u = User.findByAuthUserIdentity( knownUser );
		u.lastLogin = new Date();
		u.save();
	}
	
	public static User findByEmail( final String email ) {
		return getEmailUserFind( email ).findUnique();
	}
	
	private static ExpressionList< User > getEmailUserFind( final String email ) {
		return find.where().eq( "active", true ).eq( "email", email );
	}
	
	public LinkedAccount getAccountByProvider( final String providerKey ) {
		return LinkedAccount.findByProviderKey( this, providerKey );
	}
	
	public static void verify( final User unverified ) {
		// You might want to wrap this into a transaction
		unverified.emailValidated = true;
		unverified.save();
		TokenAction.deleteByUser( unverified, TokenType.EMAIL_VERIFICATION );
	}
	
	public void changePassword( final UsernamePasswordAuthUser authUser, final boolean create ) {
		LinkedAccount a = this.getAccountByProvider( authUser.getProvider() );
		if ( a == null ) {
			if ( create ) {
				a = LinkedAccount.create( authUser );
				a.setUser( this );
			} else {
				throw new RuntimeException( "Account not enabled for password usage" );
			}
		}
		a.setProviderUserId( authUser.getHashedPassword() );
		a.save();
	}
	
	public void resetPassword( final UsernamePasswordAuthUser authUser, final boolean create ) {
		// You might want to wrap this into a transaction
		this.changePassword( authUser, create );
		TokenAction.deleteByUser( this, TokenType.PASSWORD_RESET );
	}
}
