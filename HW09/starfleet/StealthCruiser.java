package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter {

	public static int numOfStealthCruisers;
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		numOfStealthCruisers += 1;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name, commissionYear, maximalSpeed, crewMembers, buildDefWep());
	}

	private static List<Weapon> buildDefWep(){
		List<Weapon> defaultList = new ArrayList<>();
		defaultList.add(new Weapon ("Laser Cannons",10,100));
		return defaultList;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int sum = 0;
		sum += super.getAnnualMaintenanceCost();
		sum += 50 * numOfStealthCruisers;

		return sum;
	}



}
