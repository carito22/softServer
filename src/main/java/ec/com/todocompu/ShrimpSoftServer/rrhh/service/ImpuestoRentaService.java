package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhImpuestoRenta;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface ImpuestoRentaService {

    public List<RhImpuestoRenta> getImpuestoRenta(String empresa, String año);
}
