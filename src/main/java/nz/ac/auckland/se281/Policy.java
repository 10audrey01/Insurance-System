package nz.ac.auckland.se281;

public abstract class Policy {

  public int findDiscount(
      Profiles loadedProfile,
      int basePremium) { // calculates the discount for the base premium of each policy
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
