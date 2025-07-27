package cr.ac.ucenfotec.waddle.beacon;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	private static final Dotenv dotenv = Dotenv.load();

	public static void main(String[] args) {
		System.setProperty("SENDGRID_USERNAME", dotenv.get("SENDGRID_USERNAME"));
		System.setProperty("SENDGRID_API_KEY", dotenv.get("SENDGRID_API_KEY"));

		SpringApplication.run(Application.class, args);
	}
}
