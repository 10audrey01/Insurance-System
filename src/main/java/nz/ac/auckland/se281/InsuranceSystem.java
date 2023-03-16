package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  /* private int numberOfProfiles;
  private ArrayList<String> profileNames;
  private ArrayList<String> profileAges; */
  // private Database profiles = new Database();
  ArrayList<Profiles> profileList = new ArrayList<Profiles>(); // profiles.getProfiles();

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).
    /*numberOfProfiles = 0;
    profileNames = new ArrayList<String>();
    profileAges = new ArrayList<String>();*/
  }

  public void printDatabase() {
    int numberOfProfiles = profileList.size();
    String strNumberOfProfiles = String.valueOf(numberOfProfiles); // convert int to string
    String profileToPrint;

    if (numberOfProfiles == 0) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "s", ".");
    } else if (numberOfProfiles == 1) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "", ":");
      profileToPrint =
          "1: " + (profileList.get(0)).getUserName() + ", " + (profileList.get(0)).getAge();
      System.out.println(profileToPrint);
    } else {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "s", ":");
      for (int i = 0; i < numberOfProfiles; i++) {
        int rank = i + 1;
        profileToPrint =
            rank + ": " + (profileList.get(i)).getUserName() + ", " + (profileList.get(i)).getAge();
        System.out.println(profileToPrint);
      }
    }
  }

  public void createNewProfile(String userName, String age) {
    int numberOfProfiles = profileList.size();
    boolean validProfile = true;
    boolean uniqueUsername = true;

    /*for (String name : profileList) { // check if userName is already in database
      if (name.equals(userName)) {
        uniqueUsername = false;
      }
    }*/
    for (int i = 0; i < numberOfProfiles; i++) {
      if ((profileList.get(i))
          .getUserName()
          .equals(
              userName)) { // got Profile in profileList at index i, then got userName and compared
        // to parameter userName
        uniqueUsername = false;
      }
    }

    if (userName.length() < 3) {
      validProfile = false;
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(userName);
    } else if (!uniqueUsername) {
      validProfile = false;
      MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(userName);
    }

    int intAge = stringAgeToInt(age); // convert parameter age to integer

    if (intAge < 0) { // check if the age is not a positive integer, including decimals/strings
      validProfile = false;
      MessageCli.INVALID_AGE.printMessage(age, userName);
    }

    if (validProfile) {
      Profiles profile = new Profiles(userName, age);
      profileList.add(profile);
      MessageCli.PROFILE_CREATED.printMessage(userName, age);
    }
  }

  public int stringAgeToInt(String age) { // created own method to convert age to a POSITIVE integer
    try {
      return Integer.parseInt(age);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  public void loadProfile(String userName) {
    // TODO: Complete this method.
  }

  public void unloadProfile() {
    // TODO: Complete this method.
  }

  public void deleteProfile(String userName) {
    // TODO: Complete this method.
  }

  public void createPolicy(PolicyType type, String[] options) {
    // TODO: Complete this method.
  }
}
