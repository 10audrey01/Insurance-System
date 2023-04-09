package nz.ac.auckland.se281;

public abstract class PolicyType {

  public int findDiscount(Profiles loadedProfile, int basePremium) {
    double doubleBasePremium = basePremium;

    if (loadedProfile.getNumberOfPolicies() == 2) {
      doubleBasePremium = 0.9 * basePremium;
    } else if (loadedProfile.getNumberOfPolicies() >= 3) {
      doubleBasePremium = 0.8 * basePremium;
    }

    int discountedBasePremium = (int) doubleBasePremium;
    return discountedBasePremium;
  }
}
