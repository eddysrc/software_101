package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public class CrewWoman implements CrewMember {

	private int age;
	private int yearsInService;
	private String name;
	
	public CrewWoman(int age, int yearsInService, String name){
		this.age = age;
		this.yearsInService = yearsInService;
		this.name = name;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CrewWoman crewWoman = (CrewWoman) o;
		return age == crewWoman.age && yearsInService == crewWoman.yearsInService && Objects.equals(name, crewWoman.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, yearsInService, name);
	}
}
