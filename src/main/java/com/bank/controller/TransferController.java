package com.bank.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.AccountDao;
import com.bank.dao.TransactionDao;
import com.bank.entity.UserAccount;
import com.bank.entity.Transaction;

@WebServlet("/transfer")
public class TransferController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	TransactionDao transactionDao;
	AccountDao accountDao;

	public TransferController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		transactionDao = new TransactionDao();
		accountDao = new AccountDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("accounts", accountDao.fetchAllAccounts());
		RequestDispatcher dispatcher = request.getRequestDispatcher("transfer.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer accountToDebit = !request.getParameter("debitAccount").isEmpty()
				? Integer.valueOf(request.getParameter("debitAccount"))
				: null;
		Integer accountToCredit = !request.getParameter("creditAccount").isEmpty()
				? Integer.valueOf(request.getParameter("creditAccount"))
				: null;
		Double amount = Double.valueOf(request.getParameter("amount"));
		 if (accountToCredit == null && accountToDebit != null) {
			UserAccount debitAccount = accountDao.getUserAccount(accountToDebit);
			debitAccount.setBalance(debitAccount.getBalance() - amount);
			Transaction transaction = new Transaction(new Date(), 0.0, amount, debitAccount.getBalance(), debitAccount);
			transactionDao.saveTransaction(transaction);
			accountDao.updateUserAccount(debitAccount);
		} else if (accountToCredit != null && accountToDebit == null) {
			UserAccount creditAccount = accountDao.getUserAccount(accountToCredit);
			creditAccount.setBalance(creditAccount.getBalance() + amount);
			Transaction transaction = new Transaction(new Date(), amount, 0.0, creditAccount.getBalance(),
					creditAccount);
			transactionDao.saveTransaction(transaction);
			accountDao.updateUserAccount(creditAccount);
		} else if (accountToCredit != null && accountToDebit != null) {
			UserAccount userCreditAccount = accountDao.getUserAccount(accountToCredit);
			UserAccount userDebitAccount = accountDao.getUserAccount(accountToDebit);
			userCreditAccount.setBalance(userCreditAccount.getBalance() + amount);
			userDebitAccount.setBalance(userDebitAccount.getBalance() - amount);
			Transaction transaction = new Transaction(new Date(), amount, 0.0, userCreditAccount.getBalance(),
					userCreditAccount);
			transactionDao.saveTransaction(transaction);
			accountDao.updateUserAccount(userCreditAccount);
			transaction = new Transaction(new Date(), 0.0, amount, userDebitAccount.getBalance(), userDebitAccount);
			transactionDao.saveTransaction(transaction);
			accountDao.updateUserAccount(userDebitAccount);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("transfer.jsp");
		dispatcher.forward(request, response);
	}

}
