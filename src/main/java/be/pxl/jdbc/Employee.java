package be.pxl.jdbc;

public class Employee {

	private int id;
	private String name;
	private double salary;

	public Employee(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public double getSalary() {
		return salary;
	}


	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", name='" + name + '\'' +
				", salary=" + salary +
				'}';
	}
}
