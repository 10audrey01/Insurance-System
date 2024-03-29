package nz.ac.auckland.se281;

public class Home extends Policy {
  private int sumInsured;
  private String address;
  private boolean rental;

  public Home(int sumInsured, String address, boolean rental) {
    this.sumInsured = sumInsured;
    this.address = address;
    this.rental = rental;
  }

  public int getSumInsured() {
    return sumInsured;
  }

  public String getAddress() {
    return address;
  }

  public boolean isRental() {
    return rental;
  }

  public int findBasePremium() { // find the base premium depending on if the home is rented or not
    double doubleBasePremium = 0;

    if (rental) {
      doubleBasePremium = 0.02 * sumInsured;
    } else {
      doubleBasePremium = 0.01 * sumInsured;
    }

    int basePremium = (int) doubleBasePremium;
    return basePremium;
  }
}
