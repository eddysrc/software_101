package il.ac.tau.cs.sw1.ex9.starfleet;
import java.util.Objects;
import java.util.Set;
abstract class MyAbsSpaceship implements Spaceship {
    private String name;
    private int commissionYear;
    private float maximalSpeed;
    protected int firePower;
    private Set<? extends CrewMember> crewMembers;

    public MyAbsSpaceship(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
        this.name = name;
        this.commissionYear = commissionYear;
        this.maximalSpeed = maximalSpeed;
        this.crewMembers = crewMembers;
        this.firePower = 10;
    }

    public String getName(){
        return this.name;
    }
    public int getCommissionYear(){
        return this.commissionYear;
    }
    public float getMaximalSpeed(){
        return this.maximalSpeed;
    }
    public int getFirePower(){
        return this.firePower;
    }
    public Set<? extends CrewMember> getCrewMembers(){
        return this.crewMembers;
    }
    public abstract int getAnnualMaintenanceCost();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyAbsSpaceship that = (MyAbsSpaceship) o;
        return commissionYear == that.commissionYear && Float.compare(that.maximalSpeed, maximalSpeed) == 0 && firePower == that.firePower && Objects.equals(name, that.name) && Objects.equals(crewMembers, that.crewMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, commissionYear, maximalSpeed, firePower, crewMembers);
    }

    public String toString(String[] arr){
        StringBuilder buildPrint = new StringBuilder();
        buildPrint.append(this.getClass().getSimpleName());
        buildPrint.append(System.lineSeparator());
        buildPrint.append("\t" + "Name=" + this.name);
        buildPrint.append(System.lineSeparator());
        buildPrint.append("\t" + "CommissionYear=" + this.commissionYear);
        buildPrint.append(System.lineSeparator());
        buildPrint.append("\t" + "MaximalSpeed=" + this.maximalSpeed);
        buildPrint.append(System.lineSeparator());
        buildPrint.append("\t" + "FirePower=" + this.firePower);
        buildPrint.append(System.lineSeparator());
        buildPrint.append("\t" + "CrewMembers=" + this.crewMembers.size());
        for (String line: arr){
            buildPrint.append(System.lineSeparator());
            buildPrint.append(line);
        }
        return buildPrint.toString();
    }
}
