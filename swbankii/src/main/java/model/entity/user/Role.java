package model.entity.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import model.entity.common.AbstractSWBankEntity;

@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=Role.FIND_ROLE_BY_ROLENAME, query="Select r FROM Role r WHERE r.rolename=:rolename")
})
@Entity
@Table(name = "rolebean", uniqueConstraints = @UniqueConstraint(columnNames = {"rolename","userId"}))
public class Role extends AbstractSWBankEntity {

	public static final String FIND_ROLE_BY_ROLENAME = "Role.FIND_ROLE_BY_ROLENAME";

	public Role() {
	}
	
	public Role(String rolename) {
		this.rolename = rolename;
	}
	
	@NotNull
	private String rolename;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId")
	private User user;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String role) {
		this.rolename = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
