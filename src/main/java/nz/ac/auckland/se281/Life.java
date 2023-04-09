package nz.ac.auckland.se281;

public class Life extends Policy {
  private int sumInsured;

  public Life(int sumInsured) {
    this.sumInsured = sumInsured;
  }

  public int getSumInsured() {
    return sumInsured;
  }

  public int findBasePremium(Profiles loadedProfile) {
    double doubleBasePremium;
    int intAge = Integer.parseInt(loadedProfile.getAge());

    doubleBasePremium = sumInsured * (1 + (intAge / 100.0)) / 100.0;

    int basePremium = (int) doubleBasePremium;
    return basePremium;
  }
}
