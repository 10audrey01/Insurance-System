package nz.ac.auckland.se281;

public class Life extends PolicyType {
  private int sumInsured;

  public Life(int sumInsured) {
    this.sumInsured = sumInsured;
  }

  public int getSumInsured() {
    return sumInsured;
  }

  public int findBasePremium(Profiles loadedProfile) {
    double doubleBasePremium = 0;

    doubleBasePremium = ((1 + (Integer.parseInt(loadedProfile.getAge()) / 100)) / 100) * sumInsured;

    int basePremium = (int) doubleBasePremium;
    return basePremium;
  }
}
