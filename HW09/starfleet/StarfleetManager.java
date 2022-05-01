package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;
import java.util.stream.Collectors;


public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		Comparator<Spaceship> cmpFire = Comparator.comparing(Spaceship::getFirePower).reversed();
		Comparator<Spaceship> cmpCommission = Comparator.comparing(Spaceship::getCommissionYear).reversed();
		Comparator<Spaceship> cmpName = Comparator.comparing(Spaceship::getName);

		Comparator<Spaceship> cmpFinal = cmpFire.thenComparing(cmpCommission).thenComparing(cmpName);

		List<String> rtrn = fleet.stream().sorted(cmpFinal).map(x -> x.toString()).collect(Collectors.toList());

		return rtrn;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		HashMap<String, Integer> rtrn = new HashMap<>();
		String key = null;
		for (Spaceship ship: fleet){
			key = ship.getClass().getSimpleName();
			if (rtrn.containsKey(key)) rtrn.put(key, rtrn.get(key) + 1);
			else rtrn.put(key, 1);
		}
		return  rtrn;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int sum = 0;
		for (Spaceship ship: fleet){
			sum += ship.getAnnualMaintenanceCost();
		}
		return sum;
	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> rtrn = new HashSet<>();
		List<Weapon> current = new ArrayList<>();
		for (Spaceship ship: fleet){
				if (ship instanceof Fighter){
					current = ((Fighter) ship).getWeapons();
					for (Weapon wep: current){
						rtrn.add(wep.getName());
					}
				}
		}
		return rtrn;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int count = 0;
		for (Spaceship ship: fleet){
			count += ship.getCrewMembers().size();
		}
		return count;
	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		int count = 0;
		float avg = 0;
		Set<CrewMember> current = new HashSet<>();
		for (Spaceship ship: fleet){
			current.addAll(ship.getCrewMembers());
			for (CrewMember member: current){
				if (member instanceof Officer){
					count +=1;
					avg += member.getAge();
				}
			}
			current = new HashSet<>();
		}
		return avg/count;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer, Spaceship> rtrn = new HashMap<>();
		Officer currentOfficer = null;
		Set<? extends CrewMember> crew = null;
		for (Spaceship ship: fleet){
			crew = ship.getCrewMembers();
			for (CrewMember member: crew){
				if (member instanceof Officer){
					if (currentOfficer == null) currentOfficer = (Officer) member;
					else{
						if (currentOfficer.getRank().compareTo(((Officer) member).getRank()) < 0){
							currentOfficer = (Officer) member;
						}
					}
				}
			}
			if (currentOfficer != null) rtrn.put(currentOfficer, ship);
			currentOfficer = null;
		}
		return rtrn;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank, Integer> officerRankToCounter = new HashMap<>();
		OfficerRank currentRank = null;
		for (Spaceship ship: fleet){
			for (CrewMember member: ship.getCrewMembers()){
				if (member instanceof Officer){
					currentRank = ((Officer) member).getRank();
					if (officerRankToCounter.containsKey(currentRank)) officerRankToCounter.put(currentRank, officerRankToCounter.get(currentRank) + 1);
					else officerRankToCounter.put(currentRank, 1);
				}
			}
		}

		Set<Map.Entry<OfficerRank, Integer>> entrySet = officerRankToCounter.entrySet();

		Comparator<Map.Entry<OfficerRank, Integer>> cmpCount = Comparator.comparing(Map.Entry<OfficerRank, Integer>::getValue);
		Comparator<Map.Entry<OfficerRank, Integer>> cmpRank = Comparator.comparing(Map.Entry<OfficerRank, Integer>::getKey);

		Comparator<Map.Entry<OfficerRank, Integer>> cmpFinal = cmpCount.thenComparing(cmpRank);
		return entrySet.stream().sorted(cmpFinal).collect(Collectors.toList());
	}

}
