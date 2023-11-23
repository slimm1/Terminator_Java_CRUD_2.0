package control.constants;

import java.util.regex.Pattern;

/**
 * @author Martin Ramonda
 * Variables constantes para esta app.
 */
public class Constants {
    public static String jdbc = "jdbc:sqlite:";
    public static String configFilePath = "./config/config.txt";
    public static String imgPath = "./image/logo.png";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
}
