package ch.gibm.facade;

import java.io.Serializable;
import java.util.List;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.RoleDAO;
import ch.gibm.entity.Role;

public class RoleFacade implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RoleDAO roleDAO = new RoleDAO();
	
	public void createRole(Role role) {
		EntityManagerHelper.beginTransaction();
		roleDAO.save(role);
		EntityManagerHelper.commitAndCloseTransaction();
	}
	
	public void updateRole(Role role) {
		EntityManagerHelper.beginTransaction();
		Role persistedRole = roleDAO.find(role.getId());
		persistedRole.setName(role.getName());
		EntityManagerHelper.commitAndCloseTransaction();
	}
	
	public void deleteRole(Role role){
		EntityManagerHelper.beginTransaction();
		Role persistedRole= roleDAO.find(role.getId());
		roleDAO.delete(persistedRole.getId());
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public List<Role> listAll() {
		EntityManagerHelper.beginTransaction();
		List<Role> result = roleDAO.findAll();
		EntityManagerHelper.commitAndCloseTransaction();
		return result;
	}
	
	public Role findRole(int roleId) {
		EntityManagerHelper.beginTransaction();
		Role role = roleDAO.find(roleId);
		EntityManagerHelper.commitAndCloseTransaction();
		return role;
	}
}
