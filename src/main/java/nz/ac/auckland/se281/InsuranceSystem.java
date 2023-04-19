package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  ArrayList<Profiles> profileList = new ArrayList<Profiles>();

  public InsuranceSystem() {}

  public void
      printDatabase() { // sectioned into 3 parts: 1. print profile header 2. print policy details
    // 3. print total premium
    int numberOfProfiles = profileList.size();
    String strNumberOfProfiles = String.valueOf(numberOfProfiles); // convert int to string
    boolean hasOnly1Policy = false;

    if (numberOfProfiles == 0) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "s", ".");
    } else if (numberOfProfiles == 1) { // if else statement for when there is only 1 profile
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "", ":");
      if (profileList.get(0).getNumberOfPolicies()
          == 1) { // if else statement for when there is only 1 policy for correct grammatical
        // output
        hasOnly1Policy = true;
      }
      if (hasOnly1Policy) {
        if (profileList.get(0).getProfileLoaded()) { // check if profile is loaded
          MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
              "",
              "*** 1",
              profileList.get(0).getUserName(),
              profileList.get(0).getAge(),
              "1",
              "y",
              findTotalPremium(profileList.get(0)));
          printPolicyDetails(profileList.get(0));
        } else {
          MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
              "",
              "1",
              profileList.get(0).getUserName(),
              profileList.get(0).getAge(),
              "1",
              "y",
              findTotalPremium(profileList.get(0)));
          printPolicyDetails(profileList.get(0));
        }
      } else {
        if (profileList.get(0).getProfileLoaded()) {
          MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
              "",
              "*** 1",
              profileList.get(0).getUserName(),
              profileList.get(0).getAge(),
              String.valueOf(profileList.get(0).getNumberOfPolicies()),
              "ies",
              findTotalPremium(profileList.get(0)));
          printPolicyDetails(profileList.get(0));
        } else {
          MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
              "",
              "1",
              profileList.get(0).getUserName(),
              profileList.get(0).getAge(),
              String.valueOf(profileList.get(0).getNumberOfPolicies()),
              "ies",
              findTotalPremium(profileList.get(0)));
          printPolicyDetails(profileList.get(0));
        }
      }
    } else { // same as above but for when there is more than 1 profile
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(strNumberOfProfiles, "s", ":");
      for (int i = 0; i < numberOfProfiles; i++) {
        String rank = Integer.toString(i + 1);
        hasOnly1Policy = false;

        if (profileList.get(i).getNumberOfPolicies() == 1) {
          hasOnly1Policy = true;
        }
        if (hasOnly1Policy) {
          if (profileList.get(i).getProfileLoaded()) {
            MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
                "*** ",
                rank,
                profileList.get(i).getUserName(),
                profileList.get(i).getAge(),
                "1",
                "y",
                findTotalPremium(profileList.get(i)));
            printPolicyDetails(profileList.get(i));
          } else {
            MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
                "",
                rank,
                profileList.get(i).getUserName(),
                profileList.get(i).getAge(),
                "1",
                "y",
                findTotalPremium(profileList.get(i)));
            printPolicyDetails(profileList.get(i));
          }
        } else {
          if (profileList.get(i).getProfileLoaded()) {
            MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
                "*** ",
                rank,
                profileList.get(i).getUserName(),
                profileList.get(i).getAge(),
                String.valueOf(profileList.get(i).getNumberOfPolicies()),
                "ies",
                findTotalPremium(profileList.get(i)));
            printPolicyDetails(profileList.get(i));
          } else {
            MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
                "",
                rank,
                profileList.get(i).getUserName(),
                profileList.get(i).getAge(),
                String.valueOf(profileList.get(i).getNumberOfPolicies()),
                "ies",
                findTotalPremium(profileList.get(i)));
            printPolicyDetails(profileList.get(i));
          }
        }
      }
    }
  }

  public void printPolicyDetails(
      Profiles
          profile) { // prints the details of the policies according to the type of policy, with
    // premium and discount

    for (Policy policy : profile.getPoliciesList()) {
      if (policy instanceof Home) {
        Home homePolicy = (Home) policy;
        System.out.println(
            "Home Policy ("
                + homePolicy.getAddress()
                + ", Sum Insured: $"
                + homePolicy.getSumInsured()
                + ", "
                + "Premium: $"
                + homePolicy.findBasePremium()
                + " -> $"
                + homePolicy.findDiscount(profile, homePolicy.findBasePremium())
                + ")");

      } else if (policy instanceof Car) {
        Car carPolicy = (Car) policy;
        System.out.println(
            "Car Policy ("
                + carPolicy.getMakeAndModel()
                + ", Sum Insured: $"
                + carPolicy.getSumInsured()
                + ", "
                + "Premium: $"
                + carPolicy.findBasePremium(profile)
                + " -> $"
                + carPolicy.findDiscount(profile, carPolicy.findBasePremium(profile))
                + ")");

      } else if (policy instanceof Life) {
        Life lifePolicy = (Life) policy;
        System.out.println(
            "Life Policy (Sum Insured: $"
                + lifePolicy.getSumInsured()
                + ", "
                + "Premium: $"
                + lifePolicy.findBasePremium(profile)
                + " -> $"
                + lifePolicy.findDiscount(profile, lifePolicy.findBasePremium(profile))
                + ")");
      }
    }
  }

  public String findTotalPremium(
      Profiles profile) { // calculates the total premium for the profile and returns a string for
    // print_db
    int totalPremium = 0;

    for (Policy policy : profile.getPoliciesList()) {
      if (policy instanceof Home) {
        Home homePolicy = (Home) policy;
        totalPremium += homePolicy.findDiscount(profile, homePolicy.findBasePremium());
      } else if (policy instanceof Car) {
        Car carPolicy = (Car) policy;
        totalPremium += carPolicy.findDiscount(profile, carPolicy.findBasePremium(profile));
      } else if (policy instanceof Life) {
        Life lifePolicy = (Life) policy;
        totalPremium += lifePolicy.findDiscount(profile, lifePolicy.findBasePremium(profile));
      }
    }

    return Integer.toString(totalPremium);
  }

  public void createNewProfile(String userName, String age) {
    boolean validProfile = true;
    boolean profileLoaded = false;

    String titlecaseUserName = toTitlecase(userName);

    for (int i = 0; i < profileList.size(); i++) {
      if ((profileList.get(i))
          .getUserName()
          .equals(
              titlecaseUserName)) { // got Profile in profileList at index i, then got userName and
        // compared
        // to parameter userName
        validProfile = false;
        MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(titlecaseUserName);
        break;
      }
    }

    if (userName.length() < 3) {
      validProfile = false;
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(titlecaseUserName);
    }

    int intAge = stringToPositiveInt(age); // convert parameter age to integer

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

  public int stringToPositiveInt(
      String n) { // created own method to convert age to a POSITIVE integer
    try {
      return Integer.parseInt(n);
    } catch (
        NumberFormatException
            e) { // return error (-1) if n is not a positive integer, including decimals/strings
      return -1;
    }
  }

  public boolean stringToBoolean(
      String yesOrNo) { // created own method to convert yes/no to boolean
    if (yesOrNo.toLowerCase().equals("yes")) {
      return true;
    } else if (yesOrNo.toLowerCase().equals("no")) {
      return false;
    } else if (yesOrNo.toLowerCase().equals("y")) {
      return true;
    } else if (yesOrNo.toLowerCase().equals("n")) {
      return false;
    } else {
      return false;
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
        for (int j = 0;
            j < profileList.size();
            j++) { // if there is a different previously loaded profile,
          // unload that profile and load the current profile
          if (profileList.get(j).getProfileLoaded()
              && !(profileList.get(i).getUserName().equals(profileList.get(j).getUserName()))) {

            profileList.get(j).setProfileLoadedTo(false);
          }
        }
      }
    }

    if (!profileFound) {
      System.out.println("No profile found for " + titlecaseUserName + ". Profile not loaded.");
    }
  }

  public Profiles
      findLoadedProfile() { // finds the loaded profile and returns it, used in unloadProfile and
    // createPolicy
    Profiles loadedProfile = null;

    for (int i = 0; i < profileList.size(); i++) {
      if (profileList.get(i).getProfileLoaded()) {
        loadedProfile = profileList.get(i);
      }
    }

    return loadedProfile;
  }

  public void unloadProfile() {
    Profiles loadedProfile = findLoadedProfile();

    if (loadedProfile == null) {
      System.out.println("No profile is currently loaded.");
    } else {
      System.out.println("Profile unloaded for " + loadedProfile.getUserName() + ".");
      loadedProfile.setProfileLoadedTo(false);
    }
  }

  public void deleteProfile(String userName) { // deletes profile if it is not loaded
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
      System.out.println("No profile found for " + titlecaseUserName + ". No profile was deleted.");
    }
  }

  public void createPolicy(PolicyType type, String[] options) {
    Profiles loadedProfile = findLoadedProfile();

    if (loadedProfile == null) {
      System.out.println("Need to load a profile in order to create a policy.");
    }

    if (loadedProfile != null) {
      switch (type) { // add new policy to the loaded profile according to the options parameter
          // (which scanned user input)
        case HOME:
          Home homePolicy =
              new Home(stringToPositiveInt(options[0]), options[1], stringToBoolean(options[2]));
          loadedProfile.addPolicy(homePolicy);
          System.out.println("New home policy created for " + loadedProfile.getUserName() + ".");
          break;
        case CAR:
          Car carPolicy =
              new Car(
                  stringToPositiveInt(options[0]),
                  options[1],
                  options[2],
                  stringToBoolean(options[3]));
          loadedProfile.addPolicy(carPolicy);
          System.out.println("New car policy created for " + loadedProfile.getUserName() + ".");
          break;
        case LIFE:
          if (stringToPositiveInt(loadedProfile.getAge()) > 100) {
            System.out.println(
                loadedProfile.getUserName() + " is over the age limit. No policy was created.");
          } else if (loadedProfile.hasLifePolicy()) {
            System.out.println(
                loadedProfile.getUserName()
                    + " already has a life policy. No new policy was created.");
          } else {
            Life lifePolicy = new Life(stringToPositiveInt(options[0]));
            loadedProfile.addPolicy(lifePolicy);
            System.out.println("New life policy created for " + loadedProfile.getUserName() + ".");
          }
          break;
      }
    }
  }
}
