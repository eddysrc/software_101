package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public class Cylon implements CrewMember {

	private int age;
	private int yearsInService;
	private String name;
	private int modelNumber;

	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		this.name = name;
		this.yearsInService = yearsInService;
		this.age = age;
		this.modelNumber = modelNumber;
	}


	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getAge() {
		return this.age;
	}

	@Override
	public int getYearsInService() {
		return this.yearsInService;
	}

	public int getModelNumber() {
		return this.modelNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cylon cylon = (Cylon) o;
		return age == cylon.age && yearsInService == cylon.yearsInService && modelNumber == cylon.modelNumber && Objects.equals(name, cylon.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, yearsInService, name, modelNumber);
	}
}
