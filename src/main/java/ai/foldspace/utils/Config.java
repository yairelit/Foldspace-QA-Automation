import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String EMAIL = dotenv.get("USER_EMAIL");
    public static final String PASSWORD = dotenv.get("USER_PASSWORD");
}
