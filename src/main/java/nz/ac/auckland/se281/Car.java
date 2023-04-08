package nz.ac.auckland.se281;

public class Car extends PolicyType {
  private int sumInsured;
  private String makeAndModel;
  private String licensePlate;
  private boolean mechanicalBreakdown;

  public Car(
      int sumInsured, String makeAndModel, String licensePlate, boolean mechanicalBreakdown) {
    this.sumInsured = sumInsured;
    this.makeAndModel = makeAndModel;
    this.licensePlate = licensePlate;
    this.mechanicalBreakdown = mechanicalBreakdown;
  }

  public int getSumInsured() {
    return sumInsured;
  }

  public String getMakeAndModel() {
    return makeAndModel;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public boolean isMechanicalBreakdown() {
    return mechanicalBreakdown;
  }

  public int findBasePremium(Profiles loadedProfile) {
    double doubleBasePremium = 0;

    if (Integer.parseInt(loadedProfile.getAge()) < 25) {
      doubleBasePremium = 0.15 * sumInsured;
    } else {
      doubleBasePremium = 0.1 * sumInsured;
    }

    if (mechanicalBreakdown) {
      doubleBasePremium = doubleBasePremium + 80;
    }

    int basePremium = (int) doubleBasePremium;
    return basePremium;
  }
}
