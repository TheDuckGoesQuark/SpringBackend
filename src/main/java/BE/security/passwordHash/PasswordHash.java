package BE.security.passwordHash;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class PasswordHash {

    String SECRET = "";
    int ITERATIONS = 1024; //Default Value
    int HASH_WIDTH = 160; // Default Value

    public String hashPassword(String rawPassword) {
        Pbkdf2PasswordEncoder PBKDF2encoder = new Pbkdf2PasswordEncoder(SECRET, ITERATIONS, HASH_WIDTH); // Can change width of hash
        return PBKDF2encoder.encode(rawPassword);
    }


    public boolean checkPassword(String rawpassword, String hashedPassword) {
        Pbkdf2PasswordEncoder PBKDF2matcher = new Pbkdf2PasswordEncoder(SECRET, ITERATIONS, HASH_WIDTH); // Can change width of hash
        return PBKDF2matcher.matches(rawpassword, hashedPassword);
    }
}

