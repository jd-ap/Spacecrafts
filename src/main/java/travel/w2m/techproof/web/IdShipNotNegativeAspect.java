package travel.w2m.techproof.web;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@Aspect
@Component
public class IdShipNotNegativeAspect {

    @Value("${jakarta.validation.constraints.Min.message:''}")
    private String message;

    @Before("execution(* travel.w2m.techproof.web.SpacecraftApi.getShipById(..)) && args(idShip)")
    public void doFilter(Integer idShip) {
        if (0 > idShip) {
            log.info("no puede consultar por un identificador de nave negativo");
            throw new MethodArgumentTypeMismatchException(idShip, Integer.class, "idShip", null, new RuntimeException(message));
        }


    }
}
