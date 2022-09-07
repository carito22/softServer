/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolPagoNotificaciones;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mario
 */
@Service
public class RolPagoNotificacionesServiceImpl implements RolPagoNotificacionesService {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<RhRolPagoNotificaciones> listarRolPagoNotificaciones(String contable, String fechaInicio, String fechaFin, String empresa) throws Exception {
        String porcionSql = "";
        String porcionSqlContable = "";
        if (fechaInicio != null && fechaFin != null) {
            porcionSql = porcionSql + " AND rpn_fecha >= '$$ || " + fechaInicio + "|| $$' ";
            porcionSql = porcionSql + " AND rpn_fecha <= '$$ || " + fechaFin + "|| $$' ";
        }
        if (fechaInicio != null && fechaFin != null) {
            porcionSqlContable = porcionSql + " AND rpn_contable = '$$ || " + contable + "|| $$' ";
        }
        String query = "select distinct on (rpn_contable, rpn_empresa) ROW_NUMBER() OVER() AS rpn_secuencial,"
                + "  rpn_destinatario,"
                + "  rpn_fecha,"
                + "  rpn_tipo,"
                + "  rpn_observacion,"
                + "  rpn_informe,"
                + "  rpn_contable,"
                + "  rpn_empresa,"
                + "  rpn_empleado,"
                + "  rpn_empresa from recursoshumanos.rh_rol_pago_notificaciones "
                + " WHERE rpn_empresa = '" + empresa + "'"
                + porcionSql + porcionSqlContable + ";";

        if (contable != null && !contable.equals("")) {
            query = "select * from recursoshumanos.rh_rol_pago_notificaciones "
                    + " WHERE rpn_empresa = '" + empresa + "' and rpn_contable = '" + contable + "' "
                    + porcionSql + porcionSqlContable + ";";
        }
        List<RhRolPagoNotificaciones> i = genericSQLDao.obtenerPorSql(query, RhRolPagoNotificaciones.class);
        return i;
    }

}
