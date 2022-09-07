/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteNotificaciones;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class InvClienteNotificacionesServiceImpl implements InvClienteNotificacionesService {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvClienteNotificaciones> listarNotificacionesElectronicas(String empresa, String cliCodigo, String motivo, int limite, int posicion) throws Exception {
        String motivoQuery = motivo != null ? "' AND n.motivo ='" + motivo + "'" : "'";
        String limiteQuery = " OFFSET " + posicion + " ROWS FETCH NEXT " + limite + " ROW ONLY ;";
        String query = "SELECT * FROM inventario.inv_cliente_notificaciones n"
                + " WHERE n.cli_empresa = '" + empresa + "' AND n.cli_codigo = '" + cliCodigo + motivoQuery + "  order by n.e_fecha " + limiteQuery;
        List<InvClienteNotificaciones> notificaciones = genericSQLDao.obtenerPorSql(query, InvClienteNotificaciones.class);
        return notificaciones;
    }
}
