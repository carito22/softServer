/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaFondoReserva;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class ContablePlanillaFondoReservaDaoImpl extends GenericDaoImpl<ConContablePlanillaFondoReserva, Integer> implements ContablePlanillaFondoReservaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<ConContablePlanillaFondoReserva> getListaConContablePlanillaFondoReserva(String empresa, String sector, String periodo) throws Exception {
        String sqlSector = sector == null ? "" : " AND sec_codigo='" + sector + "'";
        String sqlPeriodo = periodo == null ? "" : " AND con_periodo='" + periodo + "'";
        String sql = "SELECT * FROM contabilidad.con_contable_planilla_fondo_reserva WHERE sec_empresa = ('"
                + empresa + "')" + sqlSector + sqlPeriodo;

        return genericSQLDao.obtenerPorSql(sql, ConContablePlanillaFondoReserva.class);
    }

    @Override
    public boolean insertarContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisSuceso sisSuceso) throws Exception {
        insertar(conContablePlanillaFondoReserva);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisSuceso sisSuceso) throws Exception {
        actualizar(conContablePlanillaFondoReserva);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisSuceso sisSuceso) throws Exception {
        eliminar(conContablePlanillaFondoReserva);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
