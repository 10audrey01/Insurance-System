package nz.ac.auckland.se281;

public class Home extends PolicyType {
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
}
