package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MainTest.Task1.class,
  MainTest.Task2.class,
  MainTest.Task3.class,
  MainTest.YourTests.class,
})
public class MainTest {
  public static class Task1 extends CliTest {
    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_01_empty_database() throws Exception {
      runCommands(PRINT_DB);
      assertContains("Database has 0 profiles.");
    }

    @Test
    public void T1_02_add_one_client() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("New profile created for Jordan with age 21.");
      assertDoesNotContain("Database has 0 profiles", true);
    }

    @Test
    public void T1_03_add_one_client_with_info() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", PRINT_DB);
      assertContains("Database has 1 profile:");
      assertContains("New profile created for Jordan with age 21.");
      assertContains("1: Jordan, 21");
      assertDoesNotContain("Database has 0 profiles", true);
    }

    @Test
    public void T1_04_ignore_short_name() throws Exception {
      runCommands(CREATE_PROFILE, "Jo", "21", PRINT_DB);
      assertContains("Database has 0 profiles.");
      assertContains(
          "'Jo' is an invalid username, it should be at least 3 characters long. No profile was"
              + " created.");
      assertDoesNotContain("Database has 1 profiles", true);
      assertDoesNotContain("New profile created", true);
      assertDoesNotContain("21");
    }

    @Test
    public void T1_05_add_two_clients() throws Exception {
      runCommands(CREATE_PROFILE, "Jordan", "21", CREATE_PROFILE, "Tom", "25", PRINT_DB);
      assertContains("Database has 2 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertDoesNotContain("Database has 0 profiles", true);
      assertDoesNotContain("Database has 1 profile", true);
    }

    @Test
    public void T1_06_username_to_titlecase() throws Exception {
      runCommands(CREATE_PROFILE, "jorDan", "21", CREATE_PROFILE, "TOM", "25", PRINT_DB);
      assertContains("Database has 2 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertDoesNotContain("jorDan");
      assertDoesNotContain("TOM");
    }
  }

  public static class Task2 extends CliTest {
    public Task2() {
      super(Main.class);
    }

    @Test
    public void T2_01_load_profile_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Tom"));

      assertContains("Profile loaded for Tom.");
      assertDoesNotContain("No profile found for Tom. Profile not loaded.", true);
    }

    @Test
    public void T2_02_load_profile_not_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Alex"));

      assertContains("No profile found for Alex. Profile not loaded.");
      assertDoesNotContain("Profile loaded for Alex.", true);
    }

    @Test
    public void T2_03_load_profile_found_display() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Tom", PRINT_DB));

      assertContains("Profile loaded for Tom.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("*** 2: Tom, 25");
      assertContains("3: Jenny, 23");
    }

    @Test
    public void T2_04_load_profile_switch_profiles() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "tom", LOAD_PROFILE, "jenny", PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Profile loaded for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("*** 3: Jenny, 23");
      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
    }

    @Test
    public void T2_05_unload_profile() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Jenny", UNLOAD_PROFILE, PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("Profile unloaded for Jenny.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
    }

    @Test
    public void T2_06_unload_invalid_profile() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "jen", UNLOAD_PROFILE, PRINT_DB));

      assertContains("No profile is currently loaded.");

      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");

      assertDoesNotContain("*** 1: Jordan, 21", true);
      assertDoesNotContain("*** 2: Tom, 25", true);
      assertDoesNotContain("*** 3: Jenny, 23", true);
    }

    @Test
    public void T2_07_delete_profile_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, DELETE_PROFILE, "jordan", PRINT_DB));

      assertContains("Profile deleted for Jordan.");
      assertContains("Database has 2 profiles:");
      assertContains("1: Tom, 25");
      assertContains("2: Jenny, 23");
      assertDoesNotContain("Jordan, 21", true);
    }

    @Test
    public void T2_08_delete_profile_while_loaded() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "Jenny", DELETE_PROFILE, "jenny", PRINT_DB));

      assertContains("Profile loaded for Jenny.");

      assertContains("Cannot delete profile for Jenny while loaded. No profile was deleted.");
      assertDoesNotContain("Profile deleted for Jenny", true);

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21");
      assertContains("2: Tom, 25");
      assertContains("3: Jenny, 23");
    }
  }

  public static class Task3 extends CliTest {
    public Task3() {
      super(Main.class);
    }

    @Test
    public void T3_01_cannot_add_policy_without_loaded_profile() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, POLICY_HOME, options("1000000", "20 Symonds Street", "yes")));

      assertContains("Need to load a profile in order to create a policy.");
      assertDoesNotContain("New home policy created", true);
    }

    @Test
    public void T3_02_add_home_policy_loaded_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 1 policy");

      assertContains("New home policy created for Jenny.");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $20000)");
    }

    @Test
    public void T3_03_add_car_policy_loaded_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Tom",
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "yes"),
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("New car policy created for Tom.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("*** 2: Tom, 25, 1 policy");
      assertContains("3: Jenny, 23, 0 policies");

      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $5580 -> $5580)");
    }

    @Test
    public void T3_04_two_different_policies_home_life_one_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 2 policies");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $11070)");
    }

    @Test
    public void T3_05_three_policies_one_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "Jenny",
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"),
              POLICY_HOME,
              options("1000000", "20 Queen Street", "no"),
              POLICY_LIFE,
              options("1000000"),
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("New home policy created for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 3 profiles:");
      assertContains("1: Jordan, 21, 0 policies");
      assertContains("2: Tom, 25, 0 policies");
      assertContains("*** 3: Jenny, 23, 3 policies");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $16000)");
      assertContains(
          "Home Policy (20 Queen Street, Sum Insured: $1000000, Premium: $10000 -> $8000)");
      assertContains("Life Policy (Sum Insured: $1000000, Premium: $12300 -> $9840)");
    }

    @Test
    public void T3_06_life_policy_over_age_limit() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          101,
          LOAD_PROFILE,
          "Jenny",
          POLICY_LIFE,
          options("100000"),
          UNLOAD_PROFILE,
          PRINT_DB);

      assertContains("Profile loaded for Jenny.");
      assertContains("Jenny is over the age limit. No policy was created.");

      assertContains("Database has 1 profile:");
      assertContains("1: Jenny, 101, 0 policies");

      assertDoesNotContain("New life policy created for Jenny.", true);
      assertDoesNotContain("Life Policy (Sum Insured", true);
    }

    @Test
    public void T3_07_two_policies_one_profile_ignore_zero_policy_total_costs() throws Exception {
      runCommands(
          unpack( //
              CREATE_SOME_CLIENTS, //
              LOAD_PROFILE,
              "Tom", //
              POLICY_HOME,
              options("1000000", "20 Symonds Street", "yes"), //
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "no"), //
              UNLOAD_PROFILE, //
              LOAD_PROFILE,
              "Jenny", //
              POLICY_CAR,
              options("55000", "Subaru Impreza", "SUB123", "no"), //
              UNLOAD_PROFILE, //
              PRINT_DB));

      assertContains("2: Tom, 25, 2 policies for a total of $22950");
      assertContains("3: Jenny, 23, 1 policy for a total of $8250");

      assertContains(
          "Home Policy (20 Symonds Street, Sum Insured: $1000000, Premium: $20000 -> $18000)");
      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $5500 -> $4950)");
      assertContains("Car Policy (Subaru Impreza, Sum Insured: $55000, Premium: $8250 -> $8250)");
    }
  }

  public static class YourTests extends CliTest {
    public YourTests() {
      super(Main.class);
    }

    @Test
    public void TY_01_your_own_test() throws Exception {
      runCommands(
          PRINT_DB,
          CREATE_PROFILE,
          "jENnY",
          "twenty",
          CREATE_PROFILE,
          "audrey",
          "-34",
          CREATE_PROFILE,
          "JENNY",
          "1",
          CREATE_PROFILE,
          "jeNny",
          "4",
          PRINT_DB);
      assertContains("Database has 0 profiles.");
      assertContains(
          "'twenty' is an invalid age, please provide a positive whole number only. No profile was"
              + " created for Jenny.");
      assertContains(
          "'-34' is an invalid age, please provide a positive whole number only. No profile was"
              + " created for Audrey.");
      assertContains("New profile created for Jenny with age 1.");
      assertContains("Usernames must be unique. No profile was created for 'Jenny'.");
      assertContains("Database has 1 profile:");
      assertContains("1: Jenny, 1");
    }

    @Test
    public void TY_02_your_own_test() throws Exception {
      runCommands(
          PRINT_DB,
          CREATE_PROFILE,
          "Audrey",
          "21",
          CREATE_PROFILE,
          "Jenny",
          "21",
          CREATE_PROFILE,
          "Jello",
          "100",
          PRINT_DB);
      assertContains("Database has 0 profiles.");
      assertContains("New profile created for Audrey with age 21.");
      assertContains("New profile created for Jenny with age 21.");
      assertContains("New profile created for Jello with age 100.");
      assertContains("1: Audrey, 21");
      assertContains("2: Jenny, 21");
      assertContains("3: Jello, 100");
    }

    @Test
    public void TY_03_your_own_test() throws Exception {
      runCommands(
          CREATE_PROFILE,
          "Jenny",
          100,
          LOAD_PROFILE,
          "Jenny",
          POLICY_LIFE,
          options("100000"),
          POLICY_LIFE,
          options("100000"),
          UNLOAD_PROFILE,
          PRINT_DB);

      assertContains("Profile loaded for Jenny.");
      assertContains("New life policy created for Jenny.");

      assertContains("Database has 1 profile:");
      assertContains("1: Jenny, 100, 1 policy for a total of $2000");
      assertContains("Life Policy (Sum Insured: $100000, Premium: $2000 -> $2000)");
      assertContains("Jenny already has a life policy. No new policy was created.");
    }

    @Test
    public void T2_M01_load_invalid_profile_while_already_loaded() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "jorDAN", LOAD_PROFILE, "toBY", PRINT_DB));

      assertContains("Profile loaded for Jordan.");
      assertContains("No profile found for Toby. Profile not loaded.");
      assertContains("Database has 3 profiles:");
      assertContains("*** 1: Jordan, 21");

      assertDoesNotContain("Profile loaded for Toby.", true);
    }

    @Test
    public void T2_M02_create_profile_when_profile_loaded() throws Exception {
      runCommands(
          unpack(CREATE_SOME_CLIENTS, LOAD_PROFILE, "TOM", CREATE_PROFILE, "tOBy", "28", PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Cannot create a new profile. First unload the profile for Tom.");
      assertContains("Database has 3 profiles:");

      assertDoesNotContain("Database has 4 profiles:", true);
      assertDoesNotContain("4: Tom, 28", true);
    }

    @Test
    public void T2_M03_create_profile_after_profile_loaded() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS,
              LOAD_PROFILE,
              "toM",
              UNLOAD_PROFILE,
              CREATE_PROFILE,
              "ToBy",
              "28",
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Profile unloaded for Tom.");
      assertContains("New profile created for Toby with age 28.");
      assertContains("Database has 4 profiles:");
      assertContains("4: Toby, 28");

      assertDoesNotContain("Cannot create a new profile. First unload the profile for Tom.", true);
      assertDoesNotContain("Database has 3 profiles:", true);
    }

    @Test
    public void T2_M04_delete_profile_not_found() throws Exception {
      runCommands(unpack(CREATE_SOME_CLIENTS, DELETE_PROFILE, "samUEl", PRINT_DB));

      assertContains("Database has 3 profiles:");
      assertContains("No profile found for Samuel. No profile was deleted.");

      assertDoesNotContain("Profile deleted for Samuel.", true);
    }

    @Test
    public void T3_M01_create_car_policy_age_test() throws Exception {
      runCommands(
          unpack( //
              CREATE_SOME_CLIENTS, //
              LOAD_PROFILE,
              "ToM", //
              POLICY_CAR,
              options("35000", "Nissan Leaf", "GR333N", "no"), //
              LOAD_PROFILE,
              "jorDAN", //
              POLICY_CAR,
              options("1000", "Toyota RAV-4", "S4V3RS", "yes"), //
              PRINT_DB));

      assertContains("Profile loaded for Tom.");
      assertContains("Profile loaded for Jordan.");

      assertContains("Car Policy (Nissan Leaf, Sum Insured: $35000, Premium: $3500 -> $3500)");
      assertContains("Car Policy (Toyota RAV-4, Sum Insured: $1000, Premium: $230 -> $230)");

      assertContains(" *** 1: Jordan, 21, 1 policy for a total of $230");
      assertContains("2: Tom, 25, 1 policy for a total of $3500");
    }

    @Test
    public void T3_M02_maximum_age_boundary_life_policy() throws Exception {
      runCommands( //
          CREATE_PROFILE,
          "cAMEROn",
          "100", //
          LOAD_PROFILE,
          "CAMERon", //
          POLICY_LIFE,
          options("950000"), //
          PRINT_DB);

      assertContains("New profile created for Cameron with age 100.");
      assertContains("Profile loaded for Cameron.");

      assertContains("Life Policy (Sum Insured: $950000, Premium: $19000 -> $19000)");
    }

    @Test
    public void T3_M03_only_one_life_policy_per_profile() throws Exception {
      runCommands(
          unpack(
              CREATE_SOME_CLIENTS, //
              LOAD_PROFILE,
              "JEnnY", //
              POLICY_LIFE,
              options("15000000"), //
              LOAD_PROFILE,
              "JorDAN", //
              POLICY_LIFE,
              options("5000000"), //
              POLICY_HOME,
              options("7500000", "80 Pakuranga Road", "yes"), //
              LOAD_PROFILE,
              "JEnNY", //
              POLICY_LIFE,
              options("10000000"), //
              PRINT_DB));

      assertContains("Profile loaded for Jenny.");
      assertContains("Profile loaded for Jordan.");

      assertContains("New life policy created for Jenny.");
      assertContains("New life policy created for Jordan.");
      assertContains("New home policy created for Jordan.");

      assertContains("Life Policy (Sum Insured: $15000000, Premium: $184500 -> $184500)");
      assertContains("Life Policy (Sum Insured: $5000000, Premium: $60500 -> $54450)");
      assertContains(
          "Home Policy (80 Pakuranga Road, Sum Insured: $7500000, Premium: $150000 -> $135000)");

      assertContains("Jenny already has a life policy. No new policy was created.");

      assertDoesNotContain(
          "Life Policy (Sum Insured: $10000000, Premium: $123000 -> $123000)", true);
      assertDoesNotContain(
          "Life Policy (Sum Insured: $10000000, Premium: $123000 -> $110700)", true);
      assertDoesNotContain(
          "Life Policy (Sum Insured: $15000000, Premium: $184500 -> $166050)", true);
      assertDoesNotContain("3: Jenny, 23, 2 policies", true);
    }
  }

  private static final Object[] CREATE_SOME_CLIENTS =
      new Object[] {
        CREATE_PROFILE, "Jordan", "21", //
        CREATE_PROFILE, "Tom", "25", //
        CREATE_PROFILE, "Jenny", "23",
      };

  private static Object[] unpack(Object[] commands, Object... more) {
    final List<Object> all = new ArrayList<Object>();
    all.addAll(List.of(commands));
    all.addAll(List.of(more));
    return all.toArray(new Object[all.size()]);
  }

  private static String[] options(String... options) {
    final List<String> all = new ArrayList<String>();
    all.addAll(List.of(options));
    return all.toArray(new String[all.size()]);
  }
}
