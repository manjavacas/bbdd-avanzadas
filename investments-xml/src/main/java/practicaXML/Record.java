package practicaXML;

public class Record {

	private String description;
	private double currentEuros;
	private double currentPercent;
	private double previousEuros;
	private double previousPercent;

	public Record() {

	}

	public Record(String description, double currentEuros, double currentPercent, double previousEuros,
			double previousPercent) {
		this.description = description;
		this.currentEuros = currentEuros;
		this.currentPercent = currentPercent;
		this.previousEuros = previousEuros;
		this.previousPercent = previousPercent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCurrentEuros() {
		return currentEuros;
	}

	public void setCurrentEuros(double currentEuros) {
		this.currentEuros = currentEuros;
	}

	public double getCurrentPercent() {
		return currentPercent;
	}

	public void setCurrentPercent(double currentPercent) {
		this.currentPercent = currentPercent;
	}

	public double getPreviousEuros() {
		return previousEuros;
	}

	public void setPreviousEuros(double previousEuros) {
		this.previousEuros = previousEuros;
	}

	public double getPreviousPercent() {
		return previousPercent;
	}

	public void setPreviousPercent(double previousPercent) {
		this.previousPercent = previousPercent;
	}

	@Override
	public String toString() {
		return "Record [description=" + description + ", currentEuros=" + currentEuros + ", currentPercent="
				+ currentPercent + ", previousEuros=" + previousEuros + ", previousPercent=" + previousPercent + "]\n";
	}
}
