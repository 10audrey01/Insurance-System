package nz.ac.auckland.se281;

public class Profiles {
  private String userName;
  private String age;
  private boolean profileLoaded;

  public Profiles(String userName, String age) {
    this.userName = userName;
    this.age = age;
    this.profileLoaded = false;
  }

  public String getUserName() {
    return userName;
  }

  public String getAge() {
    return age;
  }

  public void setProfileLoadedToTrue() {
    this.profileLoaded = true;
  }
}
