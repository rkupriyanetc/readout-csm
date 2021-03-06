package ck.university.diploma.csm.providers;

import ck.university.diploma.csm.providers.MyUsernamePasswordAuthProvider.MySignup;

import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.NameIdentity;

public class MyUsernamePasswordAuthUser extends UsernamePasswordAuthUser implements NameIdentity {
	
	private static final long	serialVersionUID	= 1L;
	
	private final String			name;
	
	public MyUsernamePasswordAuthUser( final MySignup signup ) {
		super( signup.getPassword(), signup.getEmail() );
		this.name = signup.getName();
	}
	
	/**
	 * Used for password reset only - do not use this to signup a user!
	 * 
	 * @param password
	 */
	public MyUsernamePasswordAuthUser( final String password ) {
		super( password, null );
		name = null;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
