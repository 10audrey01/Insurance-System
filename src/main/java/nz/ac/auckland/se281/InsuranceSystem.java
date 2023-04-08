package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  ArrayList<Profiles> profileList = new ArrayList<Profiles>();

  public InsuranceSystem() {}

  public void printDatabase() {
    int numberOfProfiles = profileList.size();
    String strNumberOfProfiles = String.valueOf(numberOfProfiles); // convert int to string
    String profileToPrint;

    if (numberOfProfiles == 0) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "s", ".");
    } else if (numberOfProfiles == 1) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "", ":");
      if (profileList.get(0).getProfileLoaded()) {
        profileToPrint =
            "*** 1: " + (profileList.get(0)).getUserName() + ", " + (profileList.get(0)).getAge();
      } else {
        profileToPrint =
            "1: " + (profileList.get(0)).getUserName() + ", " + (profileList.get(0)).getAge();
      }
      System.out.println(profileToPrint);
    } else {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "s", ":");
      for (int i = 0; i < numberOfProfiles; i++) {
        int rank = i + 1;

        if (profileList.get(i).getProfileLoaded()) {
          profileToPrint =
              "*** "
                  + rank
                  + ": "
                  + (profileList.get(i)).getUserName()
                  + ", "
                  + (profileList.get(i)).getAge();
        } else {
          profileToPrint =
              rank
                  + ": "
                  + (profileList.get(i)).getUserName()
                  + ", "
                  + (profileList.get(i)).getAge();
        }

        System.out.println(profileToPrint);
      }
    }
  }

  public void createNewProfile(String userName, String age) {
    boolean validProfile = true;
    boolean uniqueUsername = true;
    boolean profileLoaded = false;

    String titlecaseUserName = toTitlecase(userName);

    for (int i = 0; i < profileList.size(); i++) {
      if ((profileList.get(i))
          .getUserName()
          .equals(
              titlecaseUserName)) { // got Profile in profileList at index i, then got userName and
        // compared
        // to parameter userName
        uniqueUsername = false;
        break;
      }
    }

    if (userName.length() < 3) {
      validProfile = false;
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(titlecaseUserName);
    } else if (!uniqueUsername) {
      validProfile = false;
      MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(titlecaseUserName);
    }

    int intAge = stringAgeToInt(age); // convert parameter age to integer

    if (intAge < 0) {
      validProfile = false;
      MessageCli.INVALID_AGE.printMessage(age, titlecaseUserName);
    }

    for (int i = 0; i < profileList.size(); i++) {
      if (profileList.get(i).getProfileLoaded()) {
        profileLoaded = true;
        System.out.println(
            "Cannot create a new profile. First unload the profile for "
                + (profileList.get(i)).getUserName()
                + ".");
        break;
      }
    }

    if (validProfile && !profileLoaded) {
      Profiles profile = new Profiles(titlecaseUserName, age);
      profileList.add(profile);
      MessageCli.PROFILE_CREATED.printMessage(titlecaseUserName, age);
    }
  }

  public int stringAgeToInt(String age) { // created own method to convert age to a POSITIVE integer
    try {
      return Integer.parseInt(age);
    } catch (
        NumberFormatException
            e) { // return error (-1) if age is not a positive integer, including decimals/strings
      return -1;
    }
  }

  public String toTitlecase(String userName) {
    String lowerCaseUserName = userName.toLowerCase();
    String titlecaseUserName =
        lowerCaseUserName.substring(0, 1).toUpperCase() + lowerCaseUserName.substring(1);
    return titlecaseUserName;
  }

  public void loadProfile(String userName) {
    String titlecaseUserName = toTitlecase(userName);
    boolean profileFound = false;

    for (int i = 0; i < profileList.size(); i++) {
      if ((profileList.get(i)).getUserName().equals(titlecaseUserName)) {
        System.out.println("Profile loaded for " + titlecaseUserName + ".");
        profileFound = true;
        profileList.get(i).setProfileLoadedTo(true);
        for (int j = 0; j < profileList.size(); j++) {
          if (profileList.get(j).getProfileLoaded()
              && !(profileList.get(i).getUserName().equals(profileList.get(j).getUserName()))) {

            profileList.get(j).setProfileLoadedTo(false);
            // if there is a different previously loaded profile,
            // unload that profile and load the current profile
          }
        }
      }
    }

    if (!profileFound) {
      System.out.println("No profile found for " + titlecaseUserName + ". Profile not loaded.");
    }
  }

  public void unloadProfile() {
    boolean aProfileIsLoaded = false;

    for (int i = 0; i < profileList.size(); i++) {
      if (profileList.get(i).getProfileLoaded()) {
        aProfileIsLoaded = true;
        System.out.println("Profile unloaded for " + (profileList.get(i)).getUserName() + ".");
        profileList.get(i).setProfileLoadedTo(false);
        break;
      }
    }

    if (!aProfileIsLoaded) {
      System.out.println("No profile is currently loaded.");
    }
  }

  public void deleteProfile(String userName) {
    String titlecaseUserName = toTitlecase(userName);
    boolean profileFound = false;

    for (int i = 0; i < profileList.size(); i++) {
      if ((profileList.get(i)).getUserName().equals(titlecaseUserName)) {
        profileFound = true;
        if (profileList.get(i).getProfileLoaded()) {
          System.out.println(
              "Cannot delete profile for "
                  + titlecaseUserName
                  + " while loaded. No profile was deleted.");
          break;
        } else {
          profileList.remove(i);
          System.out.println("Profile deleted for " + titlecaseUserName + ".");
        }
      }
    }

    if (!profileFound) {
      System.out.println("No profile found for " + titlecaseUserName + ". Profile not deleted.");
    }
  }

  public void createPolicy(PolicyType type, String[] options) {
    // TODO: Complete this method.
  }
}
