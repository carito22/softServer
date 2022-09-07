package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VentasFormaCobroService {

    public List<InvVentasFormaCobroTO> getListaInvVentasFormaCobroTO(String empresa, boolean inactivos) throws Exception;

    public String accionInvVentasFormaCobroTO(InvVentasFormaCobroTO invVentasFormaCobroTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvVentasFormaCobroTO(InvVentasFormaCobroTO invVentasFormaCobroTO, SisInfoTO sisInfoTO) throws Exception;

    public InvVentasFormaCobroTO getInvVentasFormaCobroTO(String empresa, String ctaCodigo) throws Exception;

    public InvVentasFormaCobro buscarVentasFormaCobro(Integer secuencial) throws Exception;

}
