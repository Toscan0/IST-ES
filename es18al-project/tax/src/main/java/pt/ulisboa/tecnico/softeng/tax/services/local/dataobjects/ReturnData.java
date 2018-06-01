package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

public class ReturnData {
	private int year;
	private double value;
	
	public ReturnData() {}
	
	public ReturnData(int year, double value) {
		this.year = year;
		this.value = value;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
