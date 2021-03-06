package ck.university.diploma.csm.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.Session;
import play.mvc.Result;
import views.html.about;
import views.html.feedback;
import views.html.guide;
import views.html.index;
import views.html.login;
import views.html.officialHome;
import views.html.profile;
import views.html.rates;
import views.html.restricted;
import views.html.signup;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import ck.university.diploma.csm.models.User;
import ck.university.diploma.csm.providers.MyUsernamePasswordAuthProvider;
import ck.university.diploma.csm.providers.MyUsernamePasswordAuthProvider.MyLogin;
import ck.university.diploma.csm.providers.MyUsernamePasswordAuthProvider.MySignup;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;

public class Application extends Controller {
	
	public static final String	FLASH_MESSAGE_KEY	= "message";
	
	public static final String	FLASH_ERROR_KEY		= "error";
	
	public static Result index() {
		return ok( index.render() );
	}
	
	public static Result about() {
		return ok( about.render() );
	}
	
	public static Result feedback() {
		return ok( feedback.render() );
	}
	
	public static Result officialHome() {
		return ok( officialHome.render() );
	}
	
	public static Result guide() {
		return ok( guide.render() );
	}
	
	public static Result rates() {
		return ok( rates.render() );
	}
	
	public static User getLocalUser( final Session session ) {
		final AuthUser currentAuthUser = PlayAuthenticate.getUser( session );
		final User localUser = User.findByAuthUserIdentity( currentAuthUser );
		return localUser;
	}
	
	@SubjectPresent
	public static Result restricted() {
		final User localUser = getLocalUser( session() );
		return ok( restricted.render( localUser ) );
	}
	
	@SubjectPresent
	public static Result profile() {
		final User localUser = getLocalUser( session() );
		return ok( profile.render( localUser ) );
	}
	
	public static Result login() {
		return ok( login.render( MyUsernamePasswordAuthProvider.LOGIN_FORM ) );
	}
	
	public static Result doLogin() {
		com.feth.play.module.pa.controllers.Authenticate.noCache( response() );
		final Form< MyLogin > filledForm = MyUsernamePasswordAuthProvider.LOGIN_FORM.bindFromRequest();
		if ( filledForm.hasErrors() ) {
			// User did not fill everything properly
			return badRequest( login.render( filledForm ) );
		} else {
			// Everything was filled
			return UsernamePasswordAuthProvider.handleLogin( ctx() );
		}
	}
	
	public static Result signup() {
		return ok( signup.render( MyUsernamePasswordAuthProvider.SIGNUP_FORM ) );
	}
	
	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter( "jsRoutes", ck.university.diploma.csm.controllers.routes.javascript.Signup.forgotPassword() ) )
				.as( "text/javascript" );
	}
	
	public static Result doSignup() {
		com.feth.play.module.pa.controllers.Authenticate.noCache( response() );
		final Form< MySignup > filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM.bindFromRequest();
		if ( filledForm.hasErrors() ) {
			// User did not fill everything properly
			return badRequest( signup.render( filledForm ) );
		} else {
			// Everything was filled
			// do something with your part of the form before handling the user
			// signup
			return UsernamePasswordAuthProvider.handleSignup( ctx() );
		}
	}
	
	public static String formatTimestamp( final long t ) {
		return new SimpleDateFormat( "yyyy-dd-MM HH:mm:ss" ).format( new Date( t ) );
	}
}