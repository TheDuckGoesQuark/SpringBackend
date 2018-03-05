package BE.security.passwordHash;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class PasswordHash {

    String SECRET = "";
    int ITERATIONS = 1024; //Default Value
    int HASH_WIDTH = 160; // Default Value

    /**
     * Hashes a specific password
     * @param rawPassword password to be hashed
     * @return hashed password
     */
    public String hashPassword(String rawPassword) {
        Pbkdf2PasswordEncoder PBKDF2encoder = new Pbkdf2PasswordEncoder(SECRET, ITERATIONS, HASH_WIDTH); // Can change width of hash
        return PBKDF2encoder.encode(rawPassword);
    }

    /**
     * Checks a specific password
     * @param rawpassword password to be checked
     * @param hashedPassword hashed password to be checked
     * @return passwords match or not
     */
    public boolean checkPassword(String rawpassword, String hashedPassword) {
        Pbkdf2PasswordEncoder PBKDF2matcher = new Pbkdf2PasswordEncoder(SECRET, ITERATIONS, HASH_WIDTH); // Can change width of hash
        return PBKDF2matcher.matches(rawpassword, hashedPassword);
    }
}

