package com.bank.utils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.bank.entity.UserAccount;
import com.bank.entity.Branch;
import com.bank.entity.Transaction;
import com.bank.entity.User;

public class HibernateConnection {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties properties = new Properties();
				properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
				properties.put(Environment.URL, "jdbc:mysql://localhost:8181/bank?useSSL=false");
				properties.put(Environment.USER, "root");
				properties.put(Environment.PASS, "Yatri21");
				properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

				properties.put(Environment.SHOW_SQL, "true");

				properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

//				settings.put(Environment.HBM2DDL_AUTO, "create-drop");

				configuration.setProperties(properties);
				configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(UserAccount.class);
				configuration.addAnnotatedClass(Transaction.class);
				configuration.addAnnotatedClass(Branch.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				System.out.println("Hibernate Java Config serviceRegistry created");
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				return sessionFactory;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
