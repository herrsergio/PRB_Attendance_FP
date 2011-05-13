package PRBAS;

public class Employee {
	private String name;
	private String id;
	private String RFC;
	private String Puesto;
	private String Dept;
	
	public Employee(String name , String id, String RFC, String Puesto, String Dept ) {
		this.name   = name;
		this.id     = id;
		this.RFC    = RFC;
		this.Puesto = Puesto;
		this.Dept   = Dept;
	}
	
	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRFC() {
		return RFC;
	}
	
	public String getPuesto() {
		return Puesto;
	}
	
	public String getDept() {
		return Dept;
	}
	
	public String toString() {
		return name;
	}


}
