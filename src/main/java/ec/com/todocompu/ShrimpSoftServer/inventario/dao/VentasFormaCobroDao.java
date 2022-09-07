package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

public interface VentasFormaCobroDao extends GenericDao<InvVentasFormaCobro, Integer> {

    public List<InvVentasFormaCobroTO> getListaInvVentasFormaCobroTO(String empresa, boolean inactivos) throws Exception;

    public Boolean accionInvVentasFormaCobro(InvVentasFormaCobro invVentasFormaPago, SisSuceso sisSuceso, char accion) throws Exception;

    public InvVentasFormaCobro buscarVentasFormaCobro(Integer secuencial) throws Exception;

    public InvVentasFormaCobroTO buscarInvVentaFormaCobroTO(String empresa, String ctaCodigo) throws Exception;

    public Boolean buscarInvVentasFormaCobro(String ctaContable, String empresa, Integer secuencial) throws Exception;

}
