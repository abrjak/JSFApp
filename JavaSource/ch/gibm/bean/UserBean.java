package ch.gibm.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ch.gibm.entity.User;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean implements Serializable {
	public static final String DI_NAME = "#{userBean}";
	private static final long serialVersionUID = 1L;
	private User user;

	// TODO: Replace with Role (see Mail by JW and wait for his code
	
	public boolean isAdmin() {
		return user.isAdmin();
	}

	public boolean isDefaultUser() {
		return user.isUser();
	}

	public String logOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/pages/public/login.xhtml";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
