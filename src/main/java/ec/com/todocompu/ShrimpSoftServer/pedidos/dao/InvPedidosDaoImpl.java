package ec.com.todocompu.ShrimpSoftServer.pedidos.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class InvPedidosDaoImpl extends GenericDaoImpl<InvPedidos, InvPedidosPK> implements InvPedidosDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean insertarInvPedido(InvPedidos invPedidos, SisSuceso sisSuceso) throws Exception {
        insertar(invPedidos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarInvPedido(InvPedidos invPedidos, SisSuceso sisSuceso) throws Exception {
        actualizar(invPedidos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvPedidos obtenerInvPedidos(String pedEmpresa, String sector, String pedMotivo, String pedNumero) throws Exception {
        return obtenerPorId(InvPedidos.class, new InvPedidosPK(pedEmpresa.trim(), sector.trim(), pedMotivo.trim(), pedNumero.trim()));
    }

    @Override
    public List<InvPedidosTO> getInvPedidosTO(String empresa, String busqueda, String motivo, String tipo) throws Exception {
        String porcionSql = "";
        if ("REGISTRADORES".equals(tipo.trim())) {
            porcionSql = " where ped_empresa = '" + empresa + "' AND m.pm_codigo = '" + motivo + "' AND ped_pendiente = true ";
        } else {
            if ("APROBADORES".equals(tipo.trim())) {
                porcionSql = " where ped_empresa = '" + empresa + "' AND m.pm_codigo = '" + motivo + "' AND ped_aprobado = true ";
            } else {
                if ("EJECUTORES".equals(tipo.trim())) {
                    porcionSql = " where ped_empresa = '" + empresa + "' AND m.pm_codigo = '" + motivo + "' AND ped_ejecutado = true ";
                } else {
                    porcionSql = " where ped_empresa = '" + empresa + "' AND m.pm_codigo = '" + motivo + "' ";
                }
            }
        }
        if (busqueda != null && !busqueda.equals("")) {
            porcionSql = porcionSql + "and (ped_numero='" + busqueda + "' or ped_motivo='" + busqueda + "' or usr_empresa='" + busqueda
                    + "' or usr_registra='" + busqueda + "')";
        }
        String sql = "SELECT p.ped_numero, p.ped_empresa, p.ped_motivo, p.ped_fecha, p.ped_pendiente, p.ped_aprobado, p.ped_ejecutado,"
                + " p.ped_anulado, p.ped_monto_total, p.usr_empresa, p.usr_registra, p.usr_aprueba, p.usr_ejecuta, p.usr_fecha_inserta, p.ped_observaciones_registra,"
                + " p.ped_observaciones_aprueba, p.ped_observaciones_ejecuta, m.pm_codigo, m.pm_detalle, m.pm_empresa,"
                + " a.usr_nombre as aprobador_nombres, a.usr_apellido as aprobador_apellidos,"
                + " e.usr_nombre as ejecutor_nombres, e.usr_apellido as ejecutor_apellidos,"
                + " r.usr_nombre as registrador_nombres, r.usr_apellido as registrador_apellidos"
                + " FROM inventario.inv_pedidos p"
                + " INNER JOIN inventario.inv_pedidos_motivo m"
                + " ON p.ped_motivo = m.pm_codigo "
                + " INNER JOIN sistemaweb.sis_usuario a ON p.usr_aprueba = a.usr_codigo "
                + " INNER JOIN sistemaweb.sis_usuario e ON p.usr_ejecuta = e.usr_codigo "
                + " INNER JOIN sistemaweb.sis_usuario r ON p.usr_registra = r.usr_codigo "
                + porcionSql + ";";
        return genericSQLDao.obtenerPorSql(sql, InvPedidosTO.class);
    }

    @Override
    public boolean eliminarInvPedidos(InvPedidos invPedidos, SisSuceso sisSuceso) throws Exception, ConstraintViolationException {
        eliminar(invPedidos);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String getProximaNumeracionInvPedidos(String empresa, InvPedidos invPedidos) throws Exception {
        String sql = "SELECT num_secuencia FROM " + "inventario.inv_pedidos_numeracion WHERE (num_empresa = '" + empresa + "' AND num_sector = '" + invPedidos.getInvPedidosPK().getPedSector() + "' AND num_motivo = '" + invPedidos.getInvPedidosPK().getPedMotivo() + "')";
        String secuencia = (String) genericSQLDao.obtenerObjetoPorSql(sql);
        if (secuencia != null) {
            invPedidos.getInvPedidosPK().setPedNumero(secuencia.toString());
        } else {
            invPedidos.getInvPedidosPK().setPedNumero("0");
        }
        String cadenaCeros = "";
        int ped_numero = Integer.parseInt(invPedidos.getInvPedidosPK().getPedNumero());
        do {
            ped_numero++;
            String cadena = ped_numero + "";
            cadenaCeros = "";
            for (int i = 0; i < (7 - cadena.length()); i++) {
                cadenaCeros = cadenaCeros.concat("0");
            }
            invPedidos.getInvPedidosPK().setPedNumero(cadenaCeros + ped_numero);
        } while (obtenerInvPedidos(invPedidos.getInvPedidosPK().getPedEmpresa(), invPedidos.getInvPedidosPK().getPedSector(), invPedidos.getInvPedidosPK().getPedMotivo(), invPedidos.getInvPedidosPK().getPedNumero()) != null);

        return cadenaCeros + ped_numero;
    }
}
