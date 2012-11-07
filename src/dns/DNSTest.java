/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dns;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

    // java.sun.com/j2se/1.4.2/docs/guide/jndi/jndi-dns.html

/**
 *
 * @author Alexander Kluge
 */
public class DNSTest {

    public static void main(String[] args) throws NamingException {
        Hashtable env = new Hashtable();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        env.put("java.naming.provider.url", "dns://8.8.8.8 dns://8.8.4.4");

        DirContext ictx = new InitialDirContext(env);
        Attributes attrs1 = ictx.getAttributes("www.google.de", new String[]{"A"});
        //Attributes attrs2 = ictx.getAttributes("82.165.74.209", new String[]{"A"});

        System.out.println(attrs1);
        //System.out.println(attrs2);
    }
}
