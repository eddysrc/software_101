package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.ArrayList;
import java.util.Set;

public class TransportShip extends MyAbsSpaceship{
	private int cargoCapacity;
	private int passengerCapacity;

	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
	super(name, commissionYear, maximalSpeed, crewMembers);
	this.cargoCapacity = cargoCapacity;
	this.passengerCapacity = passengerCapacity;
	}

	public int getCargoCapacity(){
		return this.cargoCapacity;
	}
	public int getPassengerCapacity(){
		return this.passengerCapacity;
	}
	public int getAnnualMaintenanceCost(){
		return 3000 + this.cargoCapacity * 5 + this.passengerCapacity * 3;
	}
	@Override
	public String toString(){
		String[] lines = new String[3];
		lines[0] = "\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
		lines[1] = "\tCargoCapacity=" + this.getCargoCapacity();
		lines[2] = "\tPassengerCapacity" + this.getPassengerCapacity();
		return super.toString(lines);
	}
}
