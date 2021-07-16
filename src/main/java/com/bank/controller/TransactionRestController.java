package com.bank.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.AccountDao;
import com.bank.dao.TransactionDao;
import com.bank.dao.UserDao;
import com.bank.entity.ResultDTO;
import com.bank.entity.UserAccount;
import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.google.gson.Gson;

@WebServlet("/transaction/api")
public class TransactionRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionRestController() {
		super();
	}

	private TransactionDao transactionDao;
	private AccountDao accountDao;
	private UserDao userDao;

	public void init(ServletConfig config) throws ServletException {
		transactionDao = new TransactionDao();
		accountDao = new AccountDao();
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User user = authonticateUser(request);
		if(user != null) {
			String date = request.getParameter("date");
			String accountNumber = request.getParameter("accountNumber");
			UserAccount account = accountDao.fetchAccountByAccountNumber(accountNumber);
			List<Transaction> transactions = new ArrayList<Transaction>();
			if (account != null) {
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				Date transactionDate = new Date();
				try {
					transactionDate = formatter.parse(date);
				} catch (ParseException e) {
				}
				transactions = transactionDao.fetchTransactionsByDate(transactionDate, account.getId());
			}
			List<ResultDTO> result = new ArrayList<ResultDTO>();
			for (Transaction transaction : transactions) {
				result.add(new ResultDTO(transaction.getDate(), transaction.getDeposit(), transaction.getWithdraw(), transaction.getBalance(), transaction.getAccount().getAccountName(), transaction.getAccount().getAccountNumber(), transaction.getAccount().getCurrency()));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Total Transaction", transactions.size());
			map.put("result", result);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new Gson().toJson(map));
		} else {
			response.getWriter().write("Invalid Credential");
		}

	}

	private User authonticateUser(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
		    byte[] credDecoded = Base64.getDecoder().decode(authorization.substring("Basic".length()).trim());
		    String[] emailAndPassword = new String(credDecoded, StandardCharsets.UTF_8).split(":", 2);
		    return userDao.getUserByEmailAndPassword(emailAndPassword[0], emailAndPassword[1]);
		}
		return null;
	}
}
