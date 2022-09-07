/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReproceso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoEgreso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoIngreso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class PrdReprocesoDaoImpl extends GenericDaoImpl<PrdReproceso, PrdReprocesoPK> implements PrdReprocesoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private PrdReprocesoEgresoDao prdReprocesoEgresoDao;
    @Autowired
    private PrdReprocesoIngresoDao prdReprocesoIngresoDao;

    @Override
    public PrdReprocesoTO obtenerPrdReprocesoTO(String empresa, String periodo, String motivo, String numero) throws Exception {
        PrdReproceso reproceso = obtenerPorId(PrdReproceso.class, new PrdReprocesoPK(empresa, periodo, motivo, numero));
        PrdReprocesoTO reprocesoTO = ConversionesProduccion.convertirPrdReproceso_PrdReprocesoTO(reproceso);
        return reprocesoTO;
    }

    @Override
    public List<PrdReprocesoTO> getListarPrdReprocesoTO(String empresa, String motivo, String nRegistros) throws Exception {
        String limit = "";
        motivo = motivo == null ? null : "'" + motivo + "'";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM produccion.prd_reproceso p WHERE p.rep_empresa = '" + empresa + "' AND "
                + "CASE WHEN " + motivo + " IS NULL THEN TRUE ELSE (p.rep_motivo = " + motivo + ") END  "
                + limit;

        List<PrdReproceso> listado = genericSQLDao.obtenerPorSql(sql, PrdReproceso.class);
        List<PrdReprocesoTO> listadoTO = new ArrayList<>();
        listadoTO = listado.stream()
                .map(item -> ConversionesProduccion.convertirPrdReproceso_PrdReprocesoTO(item))
                .collect(Collectors.toList());

        return listadoTO;
    }

    @Override
    public PrdReproceso buscarPrdReproceso(String empresa, String perCodigo, String motCodigo, String repNumero) throws Exception {
        return obtenerPorId(PrdReproceso.class, new PrdReprocesoPK(empresa, perCodigo, motCodigo, repNumero));
    }

    public int buscarConteoUltimaNumeracionReproceso(String empCodigo, String perCodigo, String motCodigo)
            throws Exception {
        String sql = "SELECT num_secuencia FROM produccion.prd_reproceso_numeracion WHERE num_empresa = ('" + empCodigo
                + "') AND num_periodo = " + "('" + perCodigo + "') AND num_motivo = ('" + motCodigo + "')";
        Object obj = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return new Integer(String.valueOf(UtilsConversiones.convertir(obj)));
        }
        return 0;
    }

    @Override
    public MensajeTO guardarPrdReproceso(PrdReproceso reproceso, List<PrdReprocesoEgreso> listaPrdReprocesoEgreso, List<PrdReprocesoIngreso> listaPrdReprocesoIngreso, SisSuceso sisSuceso) throws Exception {
        String rellenarConCeros = "";
        MensajeTO respuesta = new MensajeTO();
        int numeracion = buscarConteoUltimaNumeracionReproceso(reproceso.getPrdReprocesoPK().getRepEmpresa(),
                reproceso.getPrdReprocesoPK().getRepPeriodo(), reproceso.getPrdReprocesoPK().getRepMotivo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            reproceso.setPrdReprocesoPK(new PrdReprocesoPK(
                    reproceso.getPrdReprocesoPK().getRepEmpresa(),
                    reproceso.getPrdReprocesoPK().getRepPeriodo(),
                    reproceso.getPrdReprocesoPK().getRepMotivo(),
                    rellenarConCeros + numeracion));

        } while (buscarPrdReproceso(
                reproceso.getPrdReprocesoPK().getRepEmpresa(),
                reproceso.getPrdReprocesoPK().getRepPeriodo(),
                reproceso.getPrdReprocesoPK().getRepMotivo(),
                rellenarConCeros + numeracion) != null);
        //suceso
        sisSuceso.setSusClave(
                reproceso.getPrdReprocesoPK().getRepPeriodo() + " | "
                + reproceso.getPrdReprocesoPK().getRepMotivo() + " | "
                + reproceso.getPrdReprocesoPK().getRepNumero());

        sisSuceso.setSusDetalle("El reproceso:" + sisSuceso.getSusClave() + " , se ha guardado correctamente.");
        insertar(reproceso);
        //Ingresos
        if (listaPrdReprocesoIngreso == null) {
            listaPrdReprocesoIngreso = new ArrayList<>();
        }
        for (int i = 0; i < listaPrdReprocesoIngreso.size(); i++) {
            listaPrdReprocesoIngreso.get(i).setPrdReproceso(reproceso);
            listaPrdReprocesoIngreso.get(i).setInvBodega(reproceso.getInvBodega());
            prdReprocesoIngresoDao.insertar(listaPrdReprocesoIngreso.get(i));
        }
        //Egresos
        if (listaPrdReprocesoEgreso == null) {
            listaPrdReprocesoEgreso = new ArrayList<>();
        }
        for (int i = 0; i < listaPrdReprocesoEgreso.size(); i++) {
            listaPrdReprocesoEgreso.get(i).setPrdReproceso(reproceso);
            listaPrdReprocesoEgreso.get(i).setInvBodega(reproceso.getInvBodega());
            prdReprocesoEgresoDao.insertar(listaPrdReprocesoEgreso.get(i));
        }
        sucesoDao.insertar(sisSuceso);

        Map<String, Object> campos = new HashMap<>();
        campos.put("reproceso", reproceso);
        respuesta.setMap(campos);

        return respuesta;
    }

    @Override
    public MensajeTO modificarPrdReproceso(PrdReproceso reproceso, List<PrdReprocesoEgreso> listaPrdReprocesoEgreso, List<PrdReprocesoEgreso> listaPrdReprocesoEgresoEliminar, List<PrdReprocesoIngreso> listaPrdReprocesoIngreso, List<PrdReprocesoIngreso> listaPrdReprocesoIngresoEliminar, SisSuceso sisSuceso) throws Exception {
        MensajeTO respuesta = new MensajeTO();
        //suceso
        sisSuceso.setSusClave(
                reproceso.getPrdReprocesoPK().getRepPeriodo() + " | "
                + reproceso.getPrdReprocesoPK().getRepMotivo() + " | "
                + reproceso.getPrdReprocesoPK().getRepNumero());

        sisSuceso.setSusDetalle("El reproceso:" + sisSuceso.getSusClave() + " , se ha modificado correctamente.");
        actualizar(reproceso);
        //Ingresos
        if (listaPrdReprocesoIngreso == null) {
            listaPrdReprocesoIngreso = new ArrayList<>();
        }
        for (int i = 0; i < listaPrdReprocesoIngreso.size(); i++) {
            listaPrdReprocesoIngreso.get(i).setPrdReproceso(reproceso);
            listaPrdReprocesoIngreso.get(i).setInvBodega(reproceso.getInvBodega());
            if (listaPrdReprocesoIngreso.get(i).getRepSecuencial() > 0) {
                prdReprocesoIngresoDao.actualizar(listaPrdReprocesoIngreso.get(i));
            } else {
                prdReprocesoIngresoDao.insertar(listaPrdReprocesoIngreso.get(i));
            }
        }
        if (!listaPrdReprocesoIngresoEliminar.isEmpty()) {
            for (int i = 0; i < listaPrdReprocesoIngresoEliminar.size(); i++) {
                listaPrdReprocesoIngresoEliminar.get(i).setPrdReproceso(reproceso);
                prdReprocesoIngresoDao.eliminar(listaPrdReprocesoIngresoEliminar.get(i));
            }
        }
        //Egresos
        if (listaPrdReprocesoEgreso == null) {
            listaPrdReprocesoEgreso = new ArrayList<>();
        }
        for (int i = 0; i < listaPrdReprocesoEgreso.size(); i++) {
            listaPrdReprocesoEgreso.get(i).setPrdReproceso(reproceso);
            listaPrdReprocesoEgreso.get(i).setInvBodega(reproceso.getInvBodega());
            if (listaPrdReprocesoEgreso.get(i).getRepSecuencial() > 0) {
                prdReprocesoEgresoDao.actualizar(listaPrdReprocesoEgreso.get(i));
            } else {
                prdReprocesoEgresoDao.insertar(listaPrdReprocesoEgreso.get(i));
            }
        }
        if (!listaPrdReprocesoEgresoEliminar.isEmpty()) {
            for (int i = 0; i < listaPrdReprocesoEgresoEliminar.size(); i++) {
                listaPrdReprocesoEgresoEliminar.get(i).setPrdReproceso(reproceso);
                prdReprocesoEgresoDao.eliminar(listaPrdReprocesoEgresoEliminar.get(i));
            }
        }
        sucesoDao.insertar(sisSuceso);

        Map<String, Object> campos = new HashMap<>();
        campos.put("reproceso", reproceso);
        respuesta.setMap(campos);

        return respuesta;
    }

    @Override
    public void actualizarPendienteSql(PrdReprocesoPK pk, boolean pendiente) throws Exception {
        String sql = "UPDATE produccion.prd_reproceso SET rep_pendiente=" + pendiente + " WHERE rep_empresa='"
                + pk.getRepEmpresa() + "' and  rep_periodo='"
                + pk.getRepPeriodo() + "' and rep_motivo='"
                + pk.getRepMotivo() + "' and rep_numero='"
                + pk.getRepNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void anularRestaurarSql(PrdReprocesoPK pk, boolean anulado) throws Exception {
        String sql = "UPDATE produccion.prd_reproceso SET rep_anulado=" + anulado + " WHERE rep_empresa='" + pk.getRepEmpresa() + "' and  rep_periodo='" + pk.getRepPeriodo() + "' and "
                + "        rep_motivo='" + pk.getRepMotivo() + "' and rep_numero='" + pk.getRepNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

}
