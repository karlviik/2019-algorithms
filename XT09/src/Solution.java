import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

class Result {

  /*
   * Complete the 'passwordCracker' function below.
   *
   * The function is expected to return a STRING.
   * The function accepts following parameters:
   *  1. STRING_ARRAY passwords
   *  2. STRING loginAttempt
   */

  public static StringBuilder passwordCracker(List<String> passwords, String loginAttempt) {
    for (String password : passwords) {
      if (loginAttempt.startsWith(password)) {
        if (password.length() == loginAttempt.length()) {
          return new StringBuilder(password);
        }
        String newLoginAttempt = loginAttempt.substring(password.length());
        StringBuilder recursiveAnswer = passwordCracker(passwords, newLoginAttempt);
        if (recursiveAnswer.toString().equals("WRONG PASSWORD")) {
          continue;
        }
        return recursiveAnswer.insert(0, " ").insert(0, password);
      }
    }
    return new StringBuilder("WRONG PASSWORD");

  }

}

public class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    int t = Integer.parseInt(bufferedReader.readLine().trim());

    IntStream.range(0, t).forEach(tItr -> {
      try {
        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> passwords = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .collect(toList());

        String loginAttempt = bufferedReader.readLine();
        HashSet<String> newNotPasswords = new HashSet<>();
        List<String> newPasswords = new ArrayList<>();

        for (String password : passwords) {
          for (String pass : passwords) {
            if (!pass.equals(password) && password.replaceAll(pass, "").equals("")) {
              System.out.println(password.replaceAll(pass, ""));
              newNotPasswords.add(password);
              break;
            }
          }
        }
        System.out.println(newNotPasswords.size());
        for (String password : passwords) {
          if (!newNotPasswords.contains(password)) {
            newPasswords.add(password);
          }
        }

        System.out.println(newPasswords.toString());

//        String result = Result.passwordCracker(newPasswords.stream().sorted(Comparator.comparing(String::length).reversed()).collect(toList()), loginAttempt);
        String result = Result.passwordCracker(newPasswords, loginAttempt).toString();


        bufferedWriter.write(result);
        bufferedWriter.newLine();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    bufferedReader.close();
    bufferedWriter.close();
  }
}
