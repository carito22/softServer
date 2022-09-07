/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReproceso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoEgreso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoIngreso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface PrdReprocesoDao extends GenericDao<PrdReproceso, PrdReprocesoPK> {

    public PrdReproceso buscarPrdReproceso(String empresa, String perCodigo, String motCodigo, String repNumero) throws Exception;

    public PrdReprocesoTO obtenerPrdReprocesoTO(String empresa, String periodo, String motivo, String numero) throws Exception;

    public List<PrdReprocesoTO> getListarPrdReprocesoTO(String empresa, String motivo, String nrRegistros) throws Exception;

    public MensajeTO guardarPrdReproceso(PrdReproceso reproceso, List<PrdReprocesoEgreso> listaPrdReprocesoEgreso, List<PrdReprocesoIngreso> listaPrdReprocesoIngreso, SisSuceso sisSuceso) throws Exception;

    public MensajeTO modificarPrdReproceso(PrdReproceso reproceso, List<PrdReprocesoEgreso> listaPrdReprocesoEgreso, List<PrdReprocesoEgreso> listaPrdReprocesoEgresoEliminar, List<PrdReprocesoIngreso> listaPrdReprocesoIngreso, List<PrdReprocesoIngreso> listaPrdReprocesoIngresoEliminar, SisSuceso sisSuceso) throws Exception;

    public void actualizarPendienteSql(PrdReprocesoPK pk, boolean pendiente) throws Exception;

    public void anularRestaurarSql(PrdReprocesoPK pk, boolean anulado) throws Exception;

}
