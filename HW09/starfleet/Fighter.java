package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends MyAbsSpaceship {

	private List<Weapon> weapons;
	private int weaponAnnualCost;

	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name, commissionYear, maximalSpeed, (Set<CrewMember>) crewMembers);
		this.weapons = weapons;
		super.firePower += calcFirePower();
		this.weaponAnnualCost = getWeaponAnnualCost();
	}

	public List<Weapon> getWeapons(){
		return this.weapons;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return 2500 + this.weaponAnnualCost + (int)(1000 * this.getMaximalSpeed());
	}

	public int getFirePower(){
		return this.firePower;
	}

	private int calcFirePower(){
		int rtrn = 0;
		for (Weapon wep: this.weapons){
			rtrn += wep.getFirePower();
		}
		return rtrn;
	}

	private int getWeaponAnnualCost() {
		int sum = 0;
		for (Weapon wep : this.weapons) {
			sum += wep.getAnnualMaintenanceCost();
		}
		return sum;
	}

	@Override
	public String toString(){
		String[] lines = new String[2];
		lines[0] = "\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
		lines[1] = "\tWeaponArray=" + this.weapons;
		return super.toString(lines);
	}
}
