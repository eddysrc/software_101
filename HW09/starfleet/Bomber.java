package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends MyAbsSpaceship{

	private List<Weapon> weapons;
	private int numberOfTechnicians;
	private int annualMaintenanceCost;


	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.weapons = weapons;
		super.firePower += calcFirePower();
		this.numberOfTechnicians = numberOfTechnicians;
		this.annualMaintenanceCost = this.calcMaintenanceCost();
	}

	private int calcFirePower(){
		int rtrn = 0;
		for (Weapon wep: this.weapons){
			rtrn += wep.getFirePower();
		}
		return rtrn;
	}

	private int calcMaintenanceCost(){
		double sum = 0;
		for (Weapon wep: this.weapons){
			sum += wep.getAnnualMaintenanceCost();
		}
		sum = (sum * (10 - this.numberOfTechnicians)) / 10;
		sum = (int) sum + 5000;
		return (int) sum;
	}

	public List<Weapon> getWeapons(){
		return this.weapons;
	}

	public int getNumberOfTechnicians(){
		return this.numberOfTechnicians;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return this.annualMaintenanceCost;
	}

	@Override
	public String toString(){
		String[] lines = new String[3];
		lines[0] = "\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
		lines[1] = "\tWeaponArray=" + this.weapons;
		lines[2] = "\tNumberOfTechnicians=" + this.numberOfTechnicians;
		return super.toString(lines);
	}
}
