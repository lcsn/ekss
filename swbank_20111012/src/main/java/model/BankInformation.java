package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@NamedQueries({
	@NamedQuery(name=BankInformation.FIND_INFORMATION_BY_ID, query="select b from BankInformation b where b.id=:id"),
	@NamedQuery(name=BankInformation.FIND_INFORMATION_BY_NAME, query="select b from BankInformation b where b.name=:name")
	})
@Entity
@Table(name="BankInformationBean", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class BankInformation implements Serializable {
	
	private static final long serialVersionUID = -6413875730345718193L;
	
	public static final String FIND_INFORMATION_BY_ID = "BankInformation.FIND_INFORMATION_BY_ID";
	public static final String FIND_INFORMATION_BY_NAME = "BankInformation.FIND_INFORMATION_BY_NAME";
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@NotNull
	private String name = "none";

	@NotNull
	private String value = "none";
	
	private String valueClass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueClass() {
		return valueClass;
	}

	public void setValueClass(String valueClass) {
		this.valueClass = valueClass;
	}
}
