package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.ParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import java.util.Map;
import org.olap4j.impl.ArrayMap;

@Service
public class ParametrosServiceImpl implements ParametrosService {

    @Autowired
    private ParametrosDao parametrosDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhParametros getRhParametros(String fechaHasta) throws Exception {
        return parametrosDao.getRhParametros(fechaHasta);
    }

    @Override
    public RhParametros obtenerRhParametrosPorCodigoRelacionTrabajo(String fechaHasta, String codigo) throws Exception {
        return parametrosDao.obtenerRhParametrosPorCodigoRelacionTrabajo(fechaHasta, codigo);
    }

    @Override
    public RhParametros obtenerUltimoParametro() throws Exception {
        return parametrosDao.obtenerUltimoParametro();
    }

    @Override
    public RhParametros insertarRhParametro(RhParametros rhParametro, SisInfoTO sisInfoTO) throws Exception {
        susClave = rhParametro.getParSecuencial() + "";
        susDetalle = "Se ingresó el parámetro con fecha " + UtilsDate.fechaFormatoString(rhParametro.getParDesde(), "dd/MM/yyyy");
        susSuceso = "INSERT";
        susTabla = "recursoshumanos.rh_parametros";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhParametro = parametrosDao.insertarRhParametro(rhParametro, sisSuceso);
        return rhParametro;
    }

    @Override
    public RhParametros modificarRhParametro(RhParametros rhParametro, SisInfoTO sisInfoTO) throws Exception {
        susClave = rhParametro.getParSecuencial() + "";
        susDetalle = "Se modificó el parámetro con fecha " + UtilsDate.fechaFormatoString(rhParametro.getParDesde(), "dd/MM/yyyy");
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_parametros";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhParametro = parametrosDao.modificarRhParametro(rhParametro, sisSuceso);
        return rhParametro;
    }

    @Override
    public boolean eliminarRhParametro(int rhParametro, SisInfoTO sisInfoTO) throws Exception {
        susClave = rhParametro + "";
        susDetalle = "Se eliminó el parámetro con secuencial: " + rhParametro;
        susSuceso = "DELETE";
        susTabla = "recursoshumanos.rh_parametros";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return parametrosDao.eliminarRhParametro(new RhParametros(rhParametro), sisSuceso);
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudConfiguracion() throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        List<RhParametros> listaParametros = parametrosDao.listaParametrosConfiguracion();
        RhParametros ultimoParametro = parametrosDao.obtenerUltimoParametro();
        respuesta.put("listaParametros", listaParametros);
        respuesta.put("ultimoParametro", ultimoParametro);
        return respuesta;
    }

}
