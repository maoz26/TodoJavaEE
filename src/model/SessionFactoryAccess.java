/**
 * Access to SessionFactory , singleton implementation
 * Created by maoztamir on 31/12/2016.
 */

package model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryAccess {

	private SessionFactory sessionFactory;
	//Singleton SessionFactoryAccess instance static var
	private static SessionFactoryAccess instance; 

	/**
	 * private constructor
	 */
	private SessionFactoryAccess() {
		//creating factory for getting sessions
		sessionFactory =  new Configuration().configure().buildSessionFactory();
	}

	/**
	 * singleton implementation
	 */
	public static SessionFactoryAccess getInstance() {
		if (instance == null) {
			instance = new SessionFactoryAccess();
		}
		return instance;
	}

	/**
	 * sessionFactory getter
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
