package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaChequesPostfechadosProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorCobrarClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorPagarProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaValorizacionActivoBiologicoTO;

public interface FlujoCajaDao {

    public List<ConFlujoCajaTO> listarFlujoDeCaja(String empresa, String fechaHasta) throws Exception;

    public List<ConFlujoCajaCuentasPorCobrarClientesTO> listarCuentasPorCobrarClientes(String empresa, String fechaHasta) throws Exception;

    public List<ConFlujoCajaCuentasPorPagarProveedoresTO> listarCuentasPorPagarProveedores(String empresa, String fechaHasta) throws Exception;

    public List<ConFlujoCajaChequesPostfechadosProveedoresTO> listarChequesPosfechados(String empresa, String fechaHasta) throws Exception;

    public List<ConFlujoCajaValorizacionActivoBiologicoTO> getListaBuscarConCuentasFlujoDetalleTO(String empresa, String fechaHasta) throws Exception;

}
