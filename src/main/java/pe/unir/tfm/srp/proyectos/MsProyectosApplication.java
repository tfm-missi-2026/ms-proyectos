package pe.unir.tfm.srp.proyectos;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("pe.unir.tfm.srp.proyectos.repository")
public class MsProyectosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProyectosApplication.class, args);
    }
}
