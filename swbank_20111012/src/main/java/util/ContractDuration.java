package util;

import java.math.BigDecimal;
/**
 * Zinssätze für Festzinskonto in Abhängigkeit der Laufzeit.
 * @author lars
 */
public enum ContractDuration {
	ONE_YEAR(new BigDecimal("1.80"), "Ein Jahr"),
	TWO_YEAR(new BigDecimal("1.90"), "Zwei Jahre"),
	THREE_YEAR(new BigDecimal("2.00"), "Drei Jahre"),
	FOUR_YEAR(new BigDecimal("2.20"), "Vier Jahre"),
	FIVE_YEAR(new BigDecimal("2.30"), "Fünf Jahre"),
	SIX_YEAR(new BigDecimal("2.40"), "Sechs Jahre");
	
	private BigDecimal interest;
	private String description;
	
	private ContractDuration(BigDecimal interest, String description) {
		this.interest = interest;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.description + " : " + getInterest() + "%";
	}

	public BigDecimal getInterest() {
		return interest;
	}
}
