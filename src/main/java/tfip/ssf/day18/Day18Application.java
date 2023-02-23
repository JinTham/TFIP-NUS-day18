package tfip.ssf.day18;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tfip.ssf.day18.Services.HttpBinService;

@SpringBootApplication
public class Day18Application implements CommandLineRunner {

	@Autowired
	private HttpBinService HttpBinSvc;

	public static void main(String[] args) {
		SpringApplication.run(Day18Application.class, args);
	}

	@Override
	public void run(String... args){
		//HttpBinSvc.get();
		HttpBinSvc.get("test","test@gmail.com");
	}
}
