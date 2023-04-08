package nz.ac.auckland.se281;

public class Profiles {
  private String userName;
  private String age;
  private boolean profileLoaded;
  private PolicyType homePolicy;
  private PolicyType carPolicy;
  private PolicyType lifePolicy;
  private int numberOfPolicies;

  public Profiles(String userName, String age) {
    this.userName = userName;
    this.age = age;
    this.profileLoaded = false;
    this.homePolicy = null;
    this.carPolicy = null;
    this.lifePolicy = null;
    this.numberOfPolicies = 0;
  }

  public String getUserName() {
    return userName;
  }

  public String getAge() {
    return age;
  }

  public boolean getProfileLoaded() {
    return this.profileLoaded;
  }

  public void setProfileLoadedTo(boolean trueOrFalse) {
    this.profileLoaded = trueOrFalse;
  }

  public PolicyType getHomePolicy() {
    return homePolicy;
  }

  public void setHomePolicy(PolicyType homePolicy) {
    this.homePolicy = homePolicy;
    numberOfPolicies++;
  }

  public PolicyType getCarPolicy() {
    return carPolicy;
  }

  public void setCarPolicy(PolicyType carPolicy) {
    this.carPolicy = carPolicy;
    numberOfPolicies++;
  }

  public PolicyType getLifePolicy() {
    return lifePolicy;
  }

  public void setLifePolicy(PolicyType lifePolicy) {
    this.lifePolicy = lifePolicy;
    numberOfPolicies++;
  }

  public int getNumberOfPolicies() {
    return numberOfPolicies;
  }
}
