package springboot.project.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*we can use 'MD5 password encoder' or 'SHA encryption algorithms', but spring recommend
 * us to use 'BCryptPasswordEncoder' a more stable and strong encryption algorithms.*/
public class PasswordGenerator {
    private static final int strength = 12;

    // BCryptPasswordEncoder method
    public static String getHashString(String input) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
        String hashedPassword = passwordEncoder.encode(input);

        return hashedPassword;
    }

    // BCryptPasswordEncoder method
    public static boolean checkHashStrings(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

//    // generate 6 characters 0-9 and a-zA-Z for password
//    public static String generateRandomPassword() {
//        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdeghijklmnopqrstuvwxyz";
//        final int N = alphabet.length();
//
//        Random r = new Random();
//        StringBuffer output = new StringBuffer();
//        for (int i = 0; i < 6; i++) {
//            output.append(alphabet.charAt(r.nextInt(N)));
//        }
//
//        return output.toString();
//    }
//
//    public static void main(String[] args) {
//        System.out.println(getHashString("123456"));
//    }
}
