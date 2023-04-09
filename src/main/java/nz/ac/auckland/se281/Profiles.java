package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Profiles {
  private String userName;
  private String age;
  private boolean profileLoaded;
  private ArrayList<PolicyType> policiesList = new ArrayList<PolicyType>();

  public Profiles(String userName, String age) {
    this.userName = userName;
    this.age = age;
    this.profileLoaded = false;
    this.policiesList = new ArrayList<PolicyType>();
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

  public void addPolicy(PolicyType policy) {
    policiesList.add(policy);
  }

  public boolean isLifePolicy() {
    for (PolicyType policy : policiesList) {
      if (policy instanceof Life) {
        return true;
      }
    }
    return false;
  }

  public int getNumberOfPolicies() {
    return policiesList.size();
  }
}
