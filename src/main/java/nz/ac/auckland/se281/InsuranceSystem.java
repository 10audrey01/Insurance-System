package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  private int numberOfProfiles;
  private ArrayList<String> profileNames;
  private ArrayList<String> profileAges;

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).
    numberOfProfiles = 0;
    profileNames = new ArrayList<String>();
    profileAges = new ArrayList<String>();
  }

  public void printDatabase() {
    String strNumberOfProfiles = String.valueOf(numberOfProfiles); // convert int to string
    if (numberOfProfiles == 0) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "s",".");
    } 
    else if (numberOfProfiles == 1) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "", ":");
    }
    else {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "s", ":");
    }
  }

  public void createNewProfile(String userName, String age) {
    boolean validProfile = true;
    boolean uniqueUsername = true;

    for (String name : profileNames) { // check if userName is already in database
      if (name.equals(userName)) {
        uniqueUsername = false;
      }
    }

    if (userName.length() < 3) {
      validProfile = false;
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(userName);
    }
    else if (!uniqueUsername) {
      validProfile = false;
      MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(userName);
    }

    int intAge = stringAgeToInt(age); // convert parameter age to integer

    if (intAge < 0) { // check if the age is not a positive integer, eg. decimal number, negative number or string
      validProfile = false;
      MessageCli.INVALID_AGE.printMessage(age, userName);
    }

    if (validProfile) {
      profileNames.add(userName);
      profileAges.add(age);
      MessageCli.PROFILE_CREATED.printMessage(userName, age);
      numberOfProfiles++;
    }
  }

  public int stringAgeToInt(String age) { // created own method to convert age to a POSITIVE integer
    try {
      return Integer.parseInt(age);
    } 
    catch (NumberFormatException e) {
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
