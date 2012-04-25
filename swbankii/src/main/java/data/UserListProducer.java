package data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import model.entity.User;

import controller.logic.ejb.user.UserDAO;

@RequestScoped
public class UserListProducer {

	@Inject
	private UserDAO userDAO;
	
	private List<User> users;
	
	@PostConstruct
	public void retrieveAllUsers() {
		this.users = userDAO.findAll();
	}

	@Named
	@Produces
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
