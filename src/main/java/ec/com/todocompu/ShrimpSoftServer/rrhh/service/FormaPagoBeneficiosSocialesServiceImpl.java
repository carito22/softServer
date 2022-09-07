package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormaPagoBeneficiosSocialesServiceImpl implements FormaPagoBeneficiosSocialesService {

    private Boolean comprobar = false;
    private String mensaje = "";
    private BigDecimal cero = new BigDecimal("0.00");

}
