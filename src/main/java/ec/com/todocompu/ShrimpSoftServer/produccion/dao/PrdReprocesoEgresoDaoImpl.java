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
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoEgresoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoEgreso;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class PrdReprocesoEgresoDaoImpl extends GenericDaoImpl<PrdReprocesoEgreso, Integer> implements PrdReprocesoEgresoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<PrdReprocesoEgresoTO> getListarPrdReprocesoEgresoTO(String empresa, String repPeriodo, String repMotivo, String repNumero) throws Exception {
        String sql = "SELECT * FROM produccion.prd_reproceso_egreso WHERE rep_empresa = '" + empresa
                + "' AND rep_periodo = '" + repPeriodo
                + "' AND rep_motivo = '" + repMotivo
                + "' AND rep_numero = '" + repNumero + "'";

        List<PrdReprocesoEgreso> listado = genericSQLDao.obtenerPorSql(sql, PrdReprocesoEgreso.class);
        List<PrdReprocesoEgresoTO> listadoTO = new ArrayList<>();
        if (listado != null) {
            listadoTO = listado.stream()
                    .map(item -> ConversionesProduccion.convertirPrdReprocesoEgreso_PrdReprocesoEgresoTO(item))
                    .collect(Collectors.toList());
        }
        return listadoTO;
    }

}
