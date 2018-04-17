package ch.gibm.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sun.faces.context.flash.ELFlash;

import ch.gibm.entity.Role;
import ch.gibm.entity.User;
import ch.gibm.facade.UserFacade;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean implements Serializable {
	public static final String DI_NAME = "#{userBean}";
	private static final long serialVersionUID = 1L;

	private static final String SELECTED_USER = "selectedUser";

	private Role role;
	private User user;
	private User userWithRole;
	private List<User> users;

	private UserFacade userFacade;

	public boolean isAdmin() {
		if (user.getRole().getName().equalsIgnoreCase("admin")) {
			return true;
		} else {
			return false;
		}
	}

	public String logOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/pages/public/login.xhtml";
	}

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}

		return userFacade;
	}

	public void addRoleToUser() {

	}

	public void removeRoleFromUser() {

	}

	public String editUserRoles() {
		ELFlash.getFlash().put(SELECTED_USER, user);
		return "/pages/protected/admin/role/userRoles/userRoles.xhtml";
	}

	public User getUserWithRole() {
		if (userWithRole == null) {
			user = (User) ELFlash.getFlash().get(SELECTED_USER);
			userWithRole = getUserFacade().findUserWithAllRoles(user.getId());
		}

		return userWithRole;
	}

	public List<User> getAllUsers() {
		if (users == null) {
			loadPersons();
		}

		return users;
	}

	private void loadPersons() {
		users = getUserFacade().listAll();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void resetUser() {
		user = new User();
	}

	public void resetRole() {
		role = new Role();
	}

	public void setUserWithRole(User userWithRole) {
		this.userWithRole = userWithRole;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
