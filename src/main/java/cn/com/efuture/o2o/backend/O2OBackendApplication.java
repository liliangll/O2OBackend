package cn.com.efuture.o2o.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class O2OBackendApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(O2OBackendApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(O2OBackendApplication.class, args);
	}
}
