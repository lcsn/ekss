package model.entity.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractSWBankEntity implements Serializable {

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(name = "id", unique = true)
	private String id;

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}
	
}
