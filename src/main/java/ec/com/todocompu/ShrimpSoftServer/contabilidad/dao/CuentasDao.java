package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CuentasDao extends GenericDao<ConCuentas, ConCuentasPK> {

    public boolean insertarConCuenta(ConCuentas conCuentas, SisSuceso sisSuceso) throws Exception;

    public boolean insertarListadoConCuenta(List<ConCuentas> listadoConCuentas, List<SisSuceso> listadoSisSuceso) throws Exception;

    public boolean modificarConCuenta(ConCuentas conCuentas, SisSuceso sisSuceso) throws Exception;

    public boolean modificarConCuentaLlavePrincipal(ConCuentas conCuentasEliminar, ConCuentas conCuentas,
            SisSuceso sisSuceso) throws Exception;

    public boolean modificarListaConCuenta(List<ConCuentas> listaConCuentas) throws Exception;

    public boolean eliminarConCuenta(ConCuentas conCuentas, SisSuceso sisSuceso) throws Exception;

    public List<ConCuentasTO> getListaConCuentasTO(String empresa) throws Exception;

    public List<ConCuentasTO> getRangoCuentasTO(String empresa, String codigoCuentaDesde, String codigoCuentaHasta,
            int largoCuenta) throws Exception;

    public List<ConCuentasFlujoTO> getListaConCuentasFlujoTO(String empresa) throws Exception;

    public List<ConCuentasTO> getListaBuscarConCuentasTO(String empresa, String buscar, String ctaGrupo)
            throws Exception;

    public ConCuentas buscarCuentas(String empresa, String cuenta) throws Exception;

    public ConCuentasTO buscarCuentasTO(String empresa, String cuenta) throws Exception;
    
    public int getConteoCuentasTO(String empresa) throws Exception;
    
    public String eliminarTodoConCuentas(String empresa) throws Exception;

    public List<ConCuentasTO> obtenerConCuentasParaSinFormatear(String empresa) throws Exception;
}
