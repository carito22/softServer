package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControl;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface EquipoControlService {

    public String insertarEquipoControl(PrdEquipoControl prdEquipoControl, SisInfoTO sisInfoTO)
            throws Exception;

    public List<PrdEquipoControl> listarEquiposControl(String empresa) throws Exception;

    public String actualizarEquipoControl(PrdEquipoControl prdEquipoControl, SisInfoTO sisInfoTO) throws Exception;
}
