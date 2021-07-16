package com.bank.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.TransactionDao;
import com.bank.entity.Transaction;

@WebServlet("/transaction")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TransactionDao transactionDao;

	public void init() {
		transactionDao = new TransactionDao();
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
				newTransactionForm(request, response);
				break;
			case "insert":
				newTransaction(request, response);
				break;
			case "delete":
				deleteTransaction(request, response);
				break;
			case "edit":
				showUpdateTransactionForm(request, response);
				break;
			case "update":
				UpdateAccountTransaction(request, response);
				break;
			default:
				listAccountTransaction(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listAccountTransaction(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Transaction> listTransaction = transactionDao.fetchAllTransaction();
		request.setAttribute("listTransaction", listTransaction);
		RequestDispatcher dispatcher = request.getRequestDispatcher("transaction-list.jsp");
		dispatcher.forward(request, response);
	}

	private void newTransactionForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("transaction-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showUpdateTransactionForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Transaction existingTransaction = transactionDao.getAccountTransaction(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("transaction-form.jsp");
		request.setAttribute("transaction", existingTransaction);
		dispatcher.forward(request, response);

	}

	private void newTransaction(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		Double deposit = Double.valueOf(request.getParameter("deposit"));
		Double withdraw = Double.valueOf(request.getParameter("withdraw"));
		Double balance = Double.valueOf(request.getParameter("balance"));
		Transaction newTransaction = new Transaction(new Date(), deposit, withdraw, balance, null);
		transactionDao.saveTransaction(newTransaction);
		response.sendRedirect("list");
	}

	private void UpdateAccountTransaction(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Double depositAmount = Double.valueOf(request.getParameter("deposit"));
		Double withdrawAmount = Double.valueOf(request.getParameter("withdraw"));
		Double balance = Double.valueOf(request.getParameter("balance"));

		Transaction transaction = new Transaction(new Date(), depositAmount, withdrawAmount, balance, null);
		transactionDao.updateTransaction(transaction);
		response.sendRedirect("list");
	}

	private void deleteTransaction(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		transactionDao.deleteTransaction(id);
		response.sendRedirect("list");
	}
}
