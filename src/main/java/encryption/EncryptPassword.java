package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.security.MessageDigest.getInstance;

public class EncryptPassword {

    public static String encryptPassword(String password) {
        MessageDigest md = null;

        try {
            md = getInstance("sha-512");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptPassword.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException();
        }

        md.update(password.getBytes());
        return Base64.getEncoder().encodeToString(md.digest());
    }

}
