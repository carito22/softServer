package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
import java.util.Date;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

public interface ParametrosDao extends GenericDao<RhParametros, String> {

    public RhParametros getRhParametros(String fechaHasta) throws Exception;

    public RhParametros obtenerUltimoParametro() throws Exception;

    public BigDecimal getSalarioMinimoVital(Date fecha);

    public boolean esUnRangoOcupado(String fechaDesde, String fechaHasta, int secuencial) throws Exception;

    public List<RhParametros> listaParametrosConfiguracion() throws Exception;

    public RhParametros insertarRhParametro(RhParametros RhParametro, SisSuceso sisSuceso) throws Exception;

    public RhParametros modificarRhParametro(RhParametros RhParametro, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarRhParametro(RhParametros RhParametro, SisSuceso sisSuceso) throws Exception;
    
    public RhParametros obtenerRhParametrosPorCodigoRelacionTrabajo(String fechaHasta, String codigo) throws Exception;

}
