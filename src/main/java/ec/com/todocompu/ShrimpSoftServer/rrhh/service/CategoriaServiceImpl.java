package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.CategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoria;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Map;
import org.hibernate.criterion.Restrictions;
import org.olap4j.impl.ArrayMap;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;
    @Autowired
    private GenericoDao<RhCategoria, RhCategoriaPK> categoriaDaoCriteria;
    
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private EstructuraDao estructuraDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String accionRhCategoria(RhCategoriaTO rhCategoriaTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = rhCategoriaTO.getCatNombre();

        if (accion == 'I') {
            susDetalle = "Se insertó la categoría " + rhCategoriaTO.getCatNombre();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó la categoría " + rhCategoriaTO.getCatNombre();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó la categoría " + rhCategoriaTO.getCatNombre();
            susSuceso = "DELETE";
        }
        susTabla = "recursoshumanos.rh_categoria";
        sisInfoTO.setEmpresa(rhCategoriaTO.getEmpCodigo());

        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO RhCategoria
        RhCategoria rhCategoria = ConversionesRRHH.convertirRhCategoriaTO_RhCategoria(rhCategoriaTO);

        if (accion == 'E') {
            ////// BUSCANDO existencia RhCategoria
            if (categoriaDao.buscarCategoria(rhCategoriaTO.getEmpCodigo(), rhCategoriaTO.getCatNombre()) != null) {
                rhCategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = categoriaDao.accionRhCategoria(rhCategoria, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra la categoría...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia RhCategoria
            if (categoriaDao.buscarCategoria(rhCategoriaTO.getEmpCodigo(), rhCategoriaTO.getCatNombre()) != null) {
                rhCategoria.setUsrFechaInserta(
                        categoriaDao.buscarCategoria(rhCategoriaTO.getEmpCodigo(), rhCategoriaTO.getCatNombre())
                                .getUsrFechaInserta());
                comprobar = categoriaDao.accionRhCategoria(rhCategoria, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra la categoría...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia RhCategoria
            if (categoriaDao.buscarCategoria(rhCategoriaTO.getEmpCodigo(), rhCategoriaTO.getCatNombre()) == null) {
                rhCategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = categoriaDao.accionRhCategoria(rhCategoria, sisSuceso, accion);
            } else {
                mensaje = "FYa existe la categoría...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa categoría " + rhCategoriaTO.getCatNombre() + " se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa categoría " + rhCategoriaTO.getCatNombre() + " se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa categoría " + rhCategoriaTO.getCatNombre() + " se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public RhCategoriaTO getRhCategoriaTO(String empCodigo, String catNombre) throws Exception {
        return categoriaDao.getCategoria(empCodigo, catNombre);
    }

    @Override
    public List<RhComboCategoriaTO> getComboRhCategoriaTO(String empresa) throws Exception {
        return categoriaDao.getComboCategoria(empresa);
    }

    @Override
    public List<RhCategoriaTO> getListaRhCategoriaTO(String empresa) throws Exception {
        return categoriaDao.getListaRhCategoriaTO(empresa);
    }

    @Override
    public List<RhCategoria> getListaRhCategoria(String empresa) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(RhCategoria.class);
        filtro.add(Restrictions.eq("rhCategoriaPK.catEmpresa", empresa));
        return categoriaDaoCriteria.listarPorCriteriaSinProyeccionesDistinct(filtro);
    }

    @Override
    public List<RhCategoriaTO> getListaRhCategoriaCuentasTO(String empresa) throws Exception {
        return categoriaDao.getListaRhCategoriaCuentasTO(empresa);
    }

    @Override
    public Map<String, Object> obtenerDatosCategoria(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        
        List<ConTipo> tiposContables = tipoDao.getListaConTipo(empresa);
        List<RhCategoriaTO> listadoCategorias = categoriaDao.getListaRhCategoriaCuentasTO(empresa);
        List<ConEstructuraTO> estructura = estructuraDao.getListaConEstructuraTO(empresa);
        
        respuesta.put("tiposContables", tiposContables);
        respuesta.put("listadoCategorias", listadoCategorias);
        respuesta.put("estructura", estructura);
        
        return respuesta;
    }
}
