package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRelacionTrabajo;
import java.util.List;

@Transactional
public interface RelacionTrabajoService {

    public List<RhRelacionTrabajo> listarRhRelacionTrabajo() throws Exception;

}
