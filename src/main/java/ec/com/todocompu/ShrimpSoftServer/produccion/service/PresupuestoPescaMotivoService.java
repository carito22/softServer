package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface PresupuestoPescaMotivoService {

    public String insertarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisInfoTO sisInfoTO) throws Exception;

    public String modificarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisInfoTO sisInfoTO) throws Exception;

    public String eliminarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisInfoTO sisInfoTO) throws Exception;

    public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivo(String empresa) throws Exception;

    public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivoTO(String empresa, boolean inactivo) throws Exception;

    public String modificarEstadoPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;
}
