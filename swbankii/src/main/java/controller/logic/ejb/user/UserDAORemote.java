package controller.logic.ejb.user;

import java.util.List;

import javax.ejb.Remote;

import model.entity.user.User;

@Remote
public interface UserDAORemote {

	public User create(User user) throws Exception;

	public User read(User user);

	public User update(User user);

	public void delete(User user);
	
	public User findUserByEmail(String email);
	
	public User findUserByUsername(String identity);

	public List<User> findAll();
	
}
