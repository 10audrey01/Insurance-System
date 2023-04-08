package nz.ac.auckland.se281;

public class Profiles {
  private String userName;
  private String age;
  private boolean profileLoaded;
  private PolicyType homePolicy;
  private PolicyType carPolicy;
  private PolicyType lifePolicy;

  public Profiles(String userName, String age) {
    this.userName = userName;
    this.age = age;
    this.profileLoaded = false;
    this.homePolicy = null;
    this.carPolicy = null;
    this.lifePolicy = null;
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
}
