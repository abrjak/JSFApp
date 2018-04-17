package ch.gibm.facade;

import java.io.Serializable;
import java.util.List;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.UserDAO;
import ch.gibm.entity.Person;
import ch.gibm.entity.User;

public class UserFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO = new UserDAO();
	
	public User isValidLogin(String userName, String password) {
		EntityManagerHelper.beginTransaction();
		User validUser = userDAO.findUserByNameAndPassword(userName, password);
		EntityManagerHelper.commitAndCloseTransaction();
		return validUser;
	}
	
	public List<User> listAll() {
		EntityManagerHelper.beginTransaction();
		List<User> result = userDAO.findAll();
		EntityManagerHelper.commitAndCloseTransaction();
		return result;
	}
	
	public User findUserWithAllRoles(int userId) {
		EntityManagerHelper.beginTransaction();
		User user = userDAO.findUserWithRole(userId);
		EntityManagerHelper.commitAndCloseTransaction();
		return user;
	}
}
