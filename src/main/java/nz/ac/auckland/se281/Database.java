package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Database {
  private ArrayList<Profiles> profiles;

  public Database() {
    profiles = new ArrayList<Profiles>();
  }

  public ArrayList<Profiles> getProfiles() {
    return profiles;
  }
}
