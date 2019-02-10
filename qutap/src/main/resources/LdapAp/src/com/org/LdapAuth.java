package com.org;
import java.util.*;
import javax.naming.*;
import java.util.regex.*;
import javax.naming.directory.*;

public class LdapAuth {
	private final static String ldapURI = "ldap://localhost:10389/o=mojo";
	private final static String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

	private static DirContext ldapContext () throws Exception {
		Hashtable<String,String> env = new Hashtable <String,String>();
		return ldapContext(env);
	}

	private static DirContext ldapContext (Hashtable <String,String>env) throws Exception {
		env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		env.put(Context.PROVIDER_URL, ldapURI);
		DirContext ctx = new InitialDirContext(env);
		return ctx;
	}

	private static String getUid (String user) throws Exception {
		DirContext ctx = ldapContext();

		String filter = "(uid=" + user + ")";
		SearchControls ctrl = new SearchControls();
		ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration answer = ctx.search("", filter, ctrl);

		String dn;
		if (answer.hasMore()) {
			SearchResult result = (SearchResult) answer.next();
			dn = result.getNameInNamespace();
		}
		else {
			dn = null;
		}
		answer.close();
		return dn;
	}

	private static boolean testBind (String dn, String password) throws Exception {
		Hashtable<String,String> env = new Hashtable <String,String>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, dn);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			ldapContext(env);
		}
		catch (javax.naming.AuthenticationException e) {
			return false;
		}
		return true;
	}

	public static void main(String args[]) throws Exception {

		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Username, ");
		String user = sc.nextLine(); 
		
		System.out.println("Enter Password, ");
		String password = sc.nextLine();
		
		String dn = getUid(user);

		if (dn != null) {
			if ( testBind( dn, password ) ) {
				System.out.println( "user '" + user + "' authentication succeeded" );
				System.exit(0);
			}
			else {
				System.out.println( "user '" + user + "' authentication failed" );
				System.exit(1);
			}
		}
		else {
			System.out.println( "user '" + user + "' not found" );
			System.exit(1);
		}
	}


}