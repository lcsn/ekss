package model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import util.TransactionType;

public class WSTransaction {

	private static final long serialVersionUID = 6226123318098698588L;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}
	
}
