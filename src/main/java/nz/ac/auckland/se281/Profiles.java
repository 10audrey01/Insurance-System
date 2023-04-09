package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Profiles {
  private String userName;
  private String age;
  private boolean profileLoaded;
  private ArrayList<Policy> policiesList = new ArrayList<Policy>();

  public Profiles(String userName, String age) {
    this.userName = userName;
    this.age = age;
    this.profileLoaded = false;
    this.policiesList = new ArrayList<Policy>();
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

  public void addPolicy(Policy policy) {
    policiesList.add(policy);
  }

  public boolean hasLifePolicy() {
    for (Policy policy : policiesList) {
      if (policy instanceof Life) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<Policy> getPoliciesList() {
    return policiesList;
  }

  public int getNumberOfPolicies() {
    return policiesList.size();
  }
}
