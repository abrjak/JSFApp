package ch.gibm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.sun.faces.context.flash.ELFlash;

import ch.gibm.entity.Person;
import ch.gibm.entity.Role;
import ch.gibm.facade.PersonFacade;
import ch.gibm.facade.RoleFacade;

@ViewScoped
@ManagedBean(name="roleBean")
public class RoleBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String SELECTED_PERSON = "selectedPerson";

	private Role role;
	private Person person;
	private List<Role> roles;
	
	//@ManagedProperty(value="#{personBean}")
	//private PersonBean personBean;
	
	private RoleFacade roleFacade;

	public void createRole() {
		try {
			getRoleFacade().createRole(role);
			closeDialog();
			displayInfoMessageToUser("Created with success");
			loadRole();
			resetRole();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving. Try again later");
			e.printStackTrace();
		}
	}

	public RoleFacade getRoleFacade() {
		if (roleFacade == null) {
			roleFacade = new RoleFacade();
		}
		return roleFacade;
	}	

	public List<Role> getAllRoles() {
		if (role == null) {
			loadRole();
		}

		return roles;
	}
	
	private void loadRole() {
		roles = getRoleFacade().listAll();
		System.out.println(roles);
	}

	public void resetRole() {
		role = new Role();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}