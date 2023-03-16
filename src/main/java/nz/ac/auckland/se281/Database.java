package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Database {
  // private int numberOfProfiles;
  private ArrayList<Profiles> profiles;

  public Database() {
    // numberOfProfiles = 0;
    profiles = new ArrayList<Profiles>();
  }

  /* public int getNumberOfProfiles() {
    return numberOfProfiles;
  }*/

  public ArrayList<Profiles> getProfiles() {
    return profiles;
  }
}
