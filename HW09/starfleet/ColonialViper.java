package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter  {

	private int wepAnnualCost;
	private int crewAnnualCost;
	private int engineAnnualCost;

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers, List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		calcWepAnnual();
		this.crewAnnualCost = crewMembers.size() * 500;
		this.engineAnnualCost = (int) (maximalSpeed * 500);
	}

	private void calcWepAnnual(){
		int sum = 0;
		for (Weapon wep: this.getWeapons()){
			sum += wep.getAnnualMaintenanceCost();
		}
		this.wepAnnualCost = sum;
	}

	@Override
	public int getAnnualMaintenanceCost(){
		return 4000 + this.wepAnnualCost + this.crewAnnualCost + this.engineAnnualCost;
	}



}
