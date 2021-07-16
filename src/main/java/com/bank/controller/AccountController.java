package com.bank.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.AccountDao;
import com.bank.dao.UserDao;
import com.bank.entity.UserAccount;
import com.bank.entity.Branch;
import com.bank.entity.User;

@WebServlet("/account")
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountDao accountDao;
	private UserDao userDao;

	public void init() {
		accountDao = new AccountDao();
		userDao = new UserDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action") == null ? "list" : request.getParameter("action");

		try {
			switch (action) {
			case "new":
				updateRequestResponse(request, response,"account.jsp");
//				showNewForm(request, response);
				break;
			case "insert":
				addAccount(request, response);
				break;
			case "delete":
				deleteUserAccount(request, response);
				break;
			case "edit":
				displayEditForm(request, response);
				break;
			case "update":
				updateUserAccount(request, response);
				break;
			default:
				fetchAccountList(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void updateRequestResponse(HttpServletRequest request, HttpServletResponse response, String pageName) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(pageName);
		dispatcher.forward(request, response);
	}

	private void fetchAccountList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<UserAccount> listAccount = accountDao.fetchAllAccounts();
		request.setAttribute("listAccount", listAccount);
		updateRequestResponse(request, response,"account-list.jsp");
//		RequestDispatcher dispatcher = request.getRequestDispatcher("account-list.jsp");
//		dispatcher.forward(request, response);
	}

//	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		RequestDispatcher dispatcher = request.getRequestDispatcher("account.jsp");
//		dispatcher.forward(request, response);
//	}

	private void displayEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		UserAccount existingAccount = accountDao.getUserAccount(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("account.jsp");
		request.setAttribute("account", existingAccount);
		dispatcher.forward(request, response);

	}

	private void addAccount(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		String accountName = request.getParameter("accountName");
		Random rand = new Random();
		String number = "";
		for (int i = 0; i < 14; i++) {
			int n = rand.nextInt(10) + 0;
			number += Integer.toString(n);
		}
		String accountNumber = number;
		String bank_code = request.getParameter("bank_code");
		String name = request.getParameter("name");
		String iban = request.getParameter("iban");
		String address = request.getParameter("address");
		String currency = request.getParameter("currency");
		Branch branch = new Branch(bank_code, name, iban, address);

		User user = userDao.getUserInfo(1);
		UserAccount newAccount = new UserAccount(accountName, accountNumber, 0.0, currency, branch);
//		user.getAccounts().add(newAccount);
		newAccount.setUser(user);
//		userDao.saveUser(user);
		accountDao.saveUserAccount(newAccount);
		response.sendRedirect("account");
	}

	private void updateUserAccount(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String accountName = request.getParameter("accountName");
		UserAccount account = new UserAccount();
		account.setId(id);
		account.setAccountName(accountName);
		accountDao.updateUserAccount(account);
		response.sendRedirect("account");
	}

	private void deleteUserAccount(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		accountDao.deleteUserAccount(id);
		response.sendRedirect("account");
	}
}
