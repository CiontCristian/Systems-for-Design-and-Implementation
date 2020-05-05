package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ui.Console;

@Configuration
@ComponentScan({"repo", "service", "ui"})
public class AppConfig {
}
