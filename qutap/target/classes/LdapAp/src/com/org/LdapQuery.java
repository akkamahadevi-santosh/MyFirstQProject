package com.org;
import java.util.Hashtable;
 
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
 
public class LdapQuery {
	public static void main(String[] args) throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:10389/");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn=Hugo Williams,ou=users,o=mojo");
		env.put(Context.SECURITY_CREDENTIALS, "pass");
		DirContext ctx = new InitialDirContext(env);
 
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> enumeration = ctx.search("ou=users,o=mojo", "(objectClass=person)", searchControls);
		while (enumeration.hasMore()) {
			SearchResult result = enumeration.next();
			Attributes attrs = result.getAttributes();
			System.out.println(String.format("User found.. %s, %s, %s", attrs.get("cn"), attrs.get("uid"), attrs.get("objectClass")));
		}
	}
}