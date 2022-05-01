package il.ac.tau.cs.sw1.ex9.starfleet;

public class Officer extends CrewWoman {

	private int age;
	private int yearsInService;
	private String name;
	private OfficerRank rank;

	public Officer(String name, int age, int yearsInService, OfficerRank rank) {
		super(age, yearsInService, name);
		this.rank = rank;
	}

	public OfficerRank getRank(){
		return this.rank;
	}
	
	
}
