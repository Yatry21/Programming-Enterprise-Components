package com.bank.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bank.entity.UserAccount;
import com.bank.utils.HibernateConnection;

@Transactional
public class AccountDao {
	private Session getCurrentSession() {
		return HibernateConnection.getSessionFactory().openSession();
	}

	public void saveUserAccount(UserAccount account) {
		Transaction transaction = null;
		try (Session session = HibernateConnection.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(account);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void updateUserAccount(UserAccount account) {
		Transaction transaction = null;
		try (Session session = HibernateConnection.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.update(account);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	public void deleteUserAccount(int id) {

		Transaction transaction = null;
		try (Session session = HibernateConnection.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			UserAccount account = session.get(UserAccount.class, id);
			if (account != null) {
				session.delete(account);
				System.out.println("account is deleted");
			}
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public UserAccount getUserAccount(int id) {
		UserAccount account = null;
		Transaction transaction = null;
		try (Session session = HibernateConnection.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			account = session.get(UserAccount.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return account;
	}

	@SuppressWarnings("unchecked")
	public List<UserAccount> fetchAllAccounts() {
		return getCurrentSession().createQuery("from UserAccount").getResultList();
	}

	public UserAccount fetchAccountByAccountNumber(String accountNumber) {
		Transaction transaction = null;
		try (Session session = HibernateConnection.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			Query<UserAccount> query = session.createQuery("from Account where accountNumber = ?1");
			query.setParameter(1, accountNumber);
			List<UserAccount> result = query.getResultList();
			// commit transaction
			transaction.commit();
			if (result != null && !result.isEmpty()) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
}