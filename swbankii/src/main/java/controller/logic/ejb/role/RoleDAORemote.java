package controller.logic.ejb.role;

import javax.ejb.Remote;

import model.entity.user.Role;

@Remote
public interface RoleDAORemote {

	public Role create(Role role) throws Exception;

	public Role read(Role role);

	public Role update(Role role);

	public void delete(Role role);
	
	public Role findRoleByRolename(String rolename);
	
}
