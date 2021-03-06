/*
 * Copyright 2012 Steve Chaloner
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ck.university.diploma.csm.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import be.objectify.deadbolt.core.models.Role;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
@Table( name = "roles" )
public class SecurityRole extends Identifier implements Role {
	
	private static final long													serialVersionUID	= 1L;
	
	public static final String												USER_ROLE_NAME		= "USER";
	
	public static final String												ADMIN_ROLE_NAME		= "ADMIN";
	
	public static final SecurityRole									USER_ROLE					= new SecurityRole( USER_ROLE_NAME );
	
	public static final SecurityRole									ADMIN_ROLE				= new SecurityRole( ADMIN_ROLE_NAME );
	
	private final String															roleName;
	
	public static final Finder< Long, SecurityRole >	find							= new Finder< Long, SecurityRole >( Long.class,
																																					SecurityRole.class );
	
	private SecurityRole( final String roleName ) {
		this.roleName = roleName;
	}
	
	@Override
	public String getName() {
		return roleName;
	}
	
	public static SecurityRole findByRoleName( String roleName ) {
		return find.where().eq( "roleName", roleName ).findUnique();
	}
}
