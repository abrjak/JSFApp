package ch.gibm.facade;

import java.io.Serializable;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.UserDAO;
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
	
	public void updateUser(User user) {
		EntityManagerHelper.beginTransaction();
		User persistedUser = userDAO.find(user.getId());
		persistedUser.setName(user.getName());
		persistedUser.setUserName(user.getUserName());
		persistedUser.setPassword(user.getPassword());
		userDAO.update(user);
		EntityManagerHelper.commitAndCloseTransaction();
	}
}
