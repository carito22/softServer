package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstructuraService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaGrupoRelacionDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaTipoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PiscinaGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoRelacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PiscinaServiceImpl implements PiscinaService {

    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private PiscinaDao piscinaDao;

    @Autowired
    private PiscinaTipoDao piscinatipoDao;
    @Autowired
    private CuentasService cuentasService;
    @Autowired
    private EstructuraService estructuraService;
    @Autowired
    private PiscinaGrupoService piscinaGrupoService;
    @Autowired
    private PiscinaTipoService piscinaTipoService;
    @Autowired
    private PiscinaGrupoRelacionService piscinaGrupoRelacionService;
    @Autowired
    private PiscinaGrupoRelacionDao piscinaGrupoRelacionDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public boolean insertarPrdPiscina(PrdPiscinaTO prdPiscinaTO, List<PrdPiscinaGrupoRelacionTO> listado, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class,
                new PrdSectorPK(prdPiscinaTO.getSecEmpresa(), prdPiscinaTO.getSecCodigo()));

        PrdPiscina prdPiscina = piscinaDao.obtenerPorId(PrdPiscina.class, new PrdPiscinaPK(
                prdPiscinaTO.getPisEmpresa(), prdPiscinaTO.getPisSector(), prdPiscinaTO.getPisNumero()));

        boolean tipoValido = true;
        PrdPiscinaTipo prdPiscinaTipo = null;
        if (prdPiscinaTO.getTipoCodigo() != null && !prdPiscinaTO.getTipoCodigo().equals("")) {
            prdPiscinaTO.setTipoEmpresa(prdPiscinaTO.getSecEmpresa());
            prdPiscinaTipo = piscinatipoDao.obtenerPorId(PrdPiscinaTipo.class,
                    new PrdPiscinaTipoPK(prdPiscinaTO.getTipoEmpresa(), prdPiscinaTO.getTipoCodigo()));
            tipoValido = prdPiscinaTipo != null ? true : false;
        }

        if ((prdPiscina == null) && (prdSector != null) && tipoValido) {
            // CREAR EL SUCESO
            susClave = prdPiscinaTO.getPisNombre();
            susDetalle = "Se insertó a la piscina " + prdPiscinaTO.getPisNombre() + ", del sector con codigo "
                    + prdPiscinaTO.getPisSector();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_piscina";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdPiscinaTO.setUsrFechaInsertaPiscina(UtilsValidacion.fechaSistema());
            prdPiscina = ConversionesProduccion.convertirPrdPiscinaTO_PrdPiscina(prdPiscinaTO);
            prdPiscina.setPrdSector(prdSector);
            if (prdPiscinaTipo != null) {
                prdPiscina.setPrdPiscinaTipo(prdPiscinaTipo);
            }
            comprobar = piscinaDao.insertarPrdPiscina(prdPiscina, sisSuceso);
            //grupos
            if (listado != null && listado.size() > 0) {
                List<PrdPiscinaGrupoRelacion> listadoRelacion = new ArrayList<>();
                for (PrdPiscinaGrupoRelacionTO item : listado) {
                    item.setGrupoEmpresa(prdPiscina.getPrdPiscinaPK().getPisEmpresa());
                    item.setPisEmpresa(prdPiscina.getPrdPiscinaPK().getPisEmpresa());
                    item.setPisSector(prdPiscina.getPrdPiscinaPK().getPisSector());
                    item.setPisNumero(prdPiscina.getPrdPiscinaPK().getPisNumero());

                    item.setUsrCodigo(prdPiscina.getUsrCodigo());
                    item.setUsrEmpresa(prdPiscina.getUsrEmpresa());
                    item.setUsrFechaInserta(prdPiscinaTO.getUsrFechaInsertaPiscina());
                    PrdPiscinaGrupoRelacion relacionGrupo = ConversionesProduccion.convertirPrdPiscinaGrupoRelacionTO_PrdPiscinaGrupoRelacion(item);
                    listadoRelacion.add(relacionGrupo);
                }
                piscinaGrupoRelacionDao.insertar(listadoRelacion);
            }
        }
        return comprobar;
    }

    @Override
    public List<PrdPiscina> getListaSectorPorEmpresa(String empresa, String sector) throws Exception {
        return piscinaDao.obtenerPorEmpresaSector(empresa, sector);

    }

    @Override
    public boolean modificarPrdPiscina(PrdPiscinaTO prdPiscinaTO, List<PrdPiscinaGrupoRelacionTO> listado, SisInfoTO sisInfoTO) throws Exception {
        prdPiscinaTO.setUsrFechaInsertaPiscina(UtilsValidacion.fechaSistema());
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class,
                new PrdSectorPK(prdPiscinaTO.getSecEmpresa().trim(), prdPiscinaTO.getSecCodigo().trim()));
        PrdPiscina prdPiscina = piscinaDao.obtenerPorEmpresaSectorPiscina(
                prdPiscinaTO.getPisEmpresa().trim(),
                prdPiscinaTO.getPisSector().trim(),
                prdPiscinaTO.getPisNumero().trim());

        boolean tipoValido = true;
        PrdPiscinaTipo prdPiscinaTipo = null;
        if (prdPiscinaTO.getTipoCodigo() != null && !prdPiscinaTO.getTipoCodigo().equals("")) {
            prdPiscinaTO.setTipoEmpresa(prdPiscinaTO.getSecEmpresa());
            prdPiscinaTipo = piscinatipoDao.obtenerPorId(PrdPiscinaTipo.class,
                    new PrdPiscinaTipoPK(prdPiscinaTO.getTipoEmpresa(), prdPiscinaTO.getTipoCodigo()));
            tipoValido = prdPiscinaTipo != null ? true : false;
        }

        if ((prdPiscina != null) && (prdSector != null) && tipoValido) {
            susClave = prdPiscinaTO.getPisNombre();
            susDetalle = "Se modificó a la piscina con codigo " + prdPiscinaTO.getPisNumero()
                    + ", del sector con código " + prdPiscinaTO.getPisSector();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_piscina";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdPiscinaTO.setUsrCodigo(prdPiscina.getUsrCodigo());
            prdPiscinaTO.setUsrFechaInsertaPiscina(
                    UtilsValidacion.fecha(prdPiscina.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            PrdPiscina prdPiscinaModificar = ConversionesProduccion.convertirPrdPiscinaTO_PrdPiscina(prdPiscinaTO);
            prdPiscinaModificar.setPrdSector(prdSector);

            if (prdPiscinaTipo != null) {
                prdPiscinaModificar.setPrdPiscinaTipo(prdPiscinaTipo);
            }
            comprobar = piscinaDao.modificarPrdPiscina(prdPiscinaModificar, sisSuceso);
            List<PrdPiscinaGrupoRelacion> listadoRelacionGrupoTOEnLaBase = piscinaGrupoRelacionService.listarPiscinaGrupoRelacion(null, prdPiscina.getPrdPiscinaPK());

            if (listado != null && !listado.isEmpty()) {
                List<PrdPiscinaGrupoRelacion> listadoRelacion = new ArrayList<>();
                for (PrdPiscinaGrupoRelacionTO item : listado) {
                    item.setGrupoEmpresa(prdPiscina.getPrdPiscinaPK().getPisEmpresa());
                    item.setPisEmpresa(prdPiscina.getPrdPiscinaPK().getPisEmpresa());
                    item.setPisSector(prdPiscina.getPrdPiscinaPK().getPisSector());
                    item.setPisNumero(prdPiscina.getPrdPiscinaPK().getPisNumero());

                    item.setUsrCodigo(prdPiscina.getUsrCodigo());
                    item.setUsrEmpresa(prdPiscina.getUsrEmpresa());
                    item.setUsrFechaInserta(prdPiscinaTO.getUsrFechaInsertaPiscina());
                    PrdPiscinaGrupoRelacion relacionGrupo = ConversionesProduccion.convertirPrdPiscinaGrupoRelacionTO_PrdPiscinaGrupoRelacion(item);
                    listadoRelacion.add(relacionGrupo);
                }

                if (listadoRelacionGrupoTOEnLaBase.size() > 0) {
                    listadoRelacion.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                        listadoRelacionGrupoTOEnLaBase.removeIf(n -> (n.getGrupoSecuencial().equals(item.getGrupoSecuencial())));
                    });
                    listadoRelacionGrupoTOEnLaBase.forEach((itemEliminar) -> {
                        piscinaGrupoRelacionDao.eliminar(itemEliminar);
                    });
                }
                piscinaGrupoRelacionDao.saveOrUpdate(listadoRelacion);
            } else {
                if (listadoRelacionGrupoTOEnLaBase.size() > 0) {
                    listadoRelacionGrupoTOEnLaBase.forEach((itemEliminar) -> {
                        piscinaGrupoRelacionDao.eliminar(itemEliminar);
                    });
                }
            }

        }
        return comprobar;
    }

    @Override
    public boolean eliminarPrdPiscina(PrdPiscinaTO prdPiscinaTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class,
                new PrdSectorPK(prdPiscinaTO.getSecEmpresa(), prdPiscinaTO.getSecCodigo()));
        PrdPiscina prdPiscina = piscinaDao.obtenerPorId(PrdPiscina.class, new PrdPiscinaPK(
                prdPiscinaTO.getPisEmpresa(), prdPiscinaTO.getPisSector(), prdPiscinaTO.getPisNumero()));
        if (piscinaDao.eliminarPrdPiscina(prdPiscinaTO.getPisEmpresa(), prdPiscinaTO.getSecCodigo(),
                prdPiscinaTO.getPisNumero()) == true) {
            susClave = prdPiscinaTO.getPisNumero();
            susDetalle = "Se eliminó a la piscina con codigo " + prdPiscinaTO.getPisNumero()
                    + ", del sector con codigo " + prdPiscinaTO.getPisSector();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_piscina";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdPiscinaTO.setUsrFechaInsertaPiscina(UtilsValidacion.fechaSistema());
            prdPiscina = ConversionesProduccion.convertirPrdPiscinaTO_PrdPiscina(prdPiscinaTO);
            prdPiscina.setPrdSector(prdSector);
            comprobar = piscinaDao.eliminarPrdPiscina(prdPiscina, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscinaTO(String empresa, String sector) throws Exception {
        return piscinaDao.getListaPiscina(empresa, sector);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscinaTO(String empresa, String sector, boolean mostrarInactivo) throws Exception {
        return piscinaDao.getListaPiscina(empresa, sector, mostrarInactivo);
    }

    @Override
    public List<PrdPiscina> getListaPiscinaPor_Empresa_Sector_Activa(String empresa, String sector, boolean activo)
            throws Exception {
        return piscinaDao.getListaPiscinaPor_Empresa_Sector_Activa(empresa, sector, activo);
    }

    @Override
    public List<PiscinaGrameajeTO> getListaPiscinasGrameaje(String empresa, String sector, String fecha)
            throws Exception {
        return piscinaDao.getListaPiscinasGrameaje(empresa, sector, fecha);
    }

    @Override
    public List<PrdListaPiscinaComboTO> getListaPiscinaTO(String empresa, boolean activo) throws Exception {
        return piscinaDao.getListaPiscina(empresa, activo);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscinaTOBusqueda(String empresa, String sector, String fecha)
            throws Exception {
        return piscinaDao.getListaPiscinaBusqueda(empresa, sector, fecha);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscinaTOBusqueda(String empresa, String sector, String fecha, String grupo, String tipo, boolean mostrarInactivos) throws Exception {
        return piscinaDao.getListaPiscinaBusqueda(empresa, sector, fecha, grupo, tipo, mostrarInactivos);
    }

    @Override
    public List<PrdComboPiscinaTO> getComboPiscinaTO(String empresa, String sector) throws Exception {
        return piscinaDao.getComboPiscina(empresa, sector);
    }

    @Override
    public PrdPiscina obtenerPorEmpresaSectorPiscina(String empresa, String sector, String piscina) throws Exception {
        return piscinaDao.obtenerPorEmpresaSectorPiscina(empresa, sector, piscina);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscinaTO(String empresa) throws Exception {
        return piscinaDao.getListaPiscina(empresa);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscinaGeneralTO(String empresa) throws Exception {
        return piscinaDao.getListaPiscina(empresa);
    }

    @Override
    public String insertarPrdPiscina(PrdPiscina prdPiscina, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        prdPiscina.getPrdPiscinaPK().setPisNumero(prdPiscina.getPrdPiscinaPK().getPisNumero().toUpperCase());

        if (piscinaDao.obtenerPorId(PrdPiscina.class, prdPiscina.getPrdPiscinaPK()) != null) {
            retorno = "FLa piscina que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(prdPiscina.getPrdPiscinaPK().getPisEmpresa());
            susDetalle = "Se insertó a la piscina " + prdPiscina.getPisNombre() + ", del sector con codigo "
                    + prdPiscina.getPrdSector().getSecNombre();
            susClave = prdPiscina.getPisNombre();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_piscina";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            prdPiscina.setPisNombre(prdPiscina.getPisNombre().toUpperCase());
            prdPiscina.setPisActiva(true);
            prdPiscina.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdPiscina.setUsrCodigo(sisInfoTO.getUsuario());
            prdPiscina.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (piscinaDao.insertarPrdPiscina(prdPiscina, sisSuceso)) {
                retorno = "TLa piscina se guardo correctamente.";
            } else {
                retorno = "FHubo un error al guardar la piscina. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarPrdPiscina(PrdPiscina prdPiscina, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdPiscina.getPrdPiscinaPK().getPisEmpresa());

        susDetalle = "Se modifico a la piscina " + prdPiscina.getPisNombre() + ", del sector con codigo "
                + prdPiscina.getPrdSector().getSecNombre();
        susClave = prdPiscina.getPisNombre();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_piscina";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdPiscina.setPisNombre(prdPiscina.getPisNombre().toUpperCase());

        if (piscinaDao.modificarPrdPiscina(prdPiscina, sisSuceso)) {
            retorno = "TLa piscina se modifico correctamente.";
        } else {
            retorno = "FHubo un error al guardar la piscina. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public boolean modificarEstadoPrdPiscina(PrdPiscinaTO prdPiscinaTO, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        prdPiscinaTO.setUsrFechaInsertaPiscina(UtilsValidacion.fechaSistema());
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class,
                new PrdSectorPK(prdPiscinaTO.getSecEmpresa().trim(), prdPiscinaTO.getSecCodigo().trim()));
        PrdPiscina prdPiscina = piscinaDao.obtenerPorId(PrdPiscina.class,
                new PrdPiscinaPK(prdPiscinaTO.getPisEmpresa().trim(), prdPiscinaTO.getPisSector().trim(),
                        prdPiscinaTO.getPisNumero().trim()));
        if ((prdPiscina != null) && (prdSector != null)) {
            prdPiscina.setPisActiva(estado);
            susClave = prdPiscinaTO.getPisNombre();
            susDetalle = "Se modificó a la piscina con codigo " + prdPiscinaTO.getPisNumero()
                    + ", del sector con código " + prdPiscinaTO.getPisSector();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_piscina";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdPiscinaTO.setPisActiva(estado);
            prdPiscinaTO.setUsrCodigo(prdPiscina.getUsrCodigo());
            prdPiscinaTO.setUsrFechaInsertaPiscina(
                    UtilsValidacion.fecha(prdPiscina.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            PrdPiscina prdPiscinaModificar = ConversionesProduccion.convertirPrdPiscinaTO_PrdPiscina(prdPiscinaTO);
            prdPiscinaModificar.setPrdSector(prdSector);
            prdPiscinaModificar.setPisActiva(estado);
            comprobar = piscinaDao.modificarPrdPiscina(prdPiscinaModificar, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public String eliminarPrdPiscina(PrdPiscina prdPiscina, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdPiscina.getPrdPiscinaPK().getPisEmpresa());
        prdPiscina.setPisNombre(prdPiscina.getPisNombre().toUpperCase());

        if (!piscinaDao.eliminarPrdPiscina(prdPiscina.getPrdPiscinaPK().getPisEmpresa(),
                prdPiscina.getPrdPiscinaPK().getPisSector(), prdPiscina.getPrdPiscinaPK().getPisNumero()) == true) {
            retorno = "FNo se puede eliminar la piscina porque presenta movimientos";
        } else {
            susDetalle = "Se eliminó a la piscina " + prdPiscina.getPisNombre() + ", del sector con codigo "
                    + prdPiscina.getPrdSector().getSecNombre();
            susClave = prdPiscina.getPisNombre();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_piscina";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            if (piscinaDao.eliminarPrdPiscina(prdPiscina, sisSuceso)) {
                retorno = "TLa piscina se eliminó correctamente.";
            } else {
                retorno = "FHubo un error al guardar la piscina. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String cambiarCodigoPiscina(String empresa, String sector, String piscinaIncorrecta, String piscinaCorrecta) throws Exception {
        String mensaje = "";
        if (piscinaDao.cambiarCodigoPiscina(empresa, sector, piscinaIncorrecta, piscinaCorrecta)) {
            mensaje = "TLa piscina se cambió correctamente.";
        } else {
            mensaje = "FHubo un error al cambiar la piscina. Intente de nuevo o contacte con el administrador";
        }
        return mensaje;
    }

    @Override
    public Map<String, Object> obtenerDetalleCuenta(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String ctaCostoDirecto = UtilsJSON.jsonToObjeto(String.class, map.get("ctaCostoDirecto"));
        String ctaCostoIndirecto = UtilsJSON.jsonToObjeto(String.class, map.get("ctaCostoIndirecto"));
        String ctaCostoProductoTerminado = UtilsJSON.jsonToObjeto(String.class, map.get("ctaCostoProductoTerminado"));
        String ctaCostoTransferencia = UtilsJSON.jsonToObjeto(String.class, map.get("ctaCostoTransferencia"));
        String ctaCostoVenta = UtilsJSON.jsonToObjeto(String.class, map.get("ctaCostoVenta"));

        List<ConCuentasTO> listaCtaCostoDirecto = cuentasService.getListaBuscarConCuentasTO(empresa, ctaCostoDirecto, null, false);
        List<ConCuentasTO> listaCtaCostoIndirecto = cuentasService.getListaBuscarConCuentasTO(empresa, ctaCostoIndirecto, null, false);
        List<ConCuentasTO> listaCtaCostoProductoTerminado = cuentasService.getListaBuscarConCuentasTO(empresa, ctaCostoProductoTerminado, null, false);
        List<ConCuentasTO> listaCtaCostoTransferencia = cuentasService.getListaBuscarConCuentasTO(empresa, ctaCostoTransferencia, null, false);
        List<ConCuentasTO> listaCtaCostoVenta = cuentasService.getListaBuscarConCuentasTO(empresa, ctaCostoVenta, null, false);

        campos.put("listaCtaCostoDirecto", listaCtaCostoDirecto);
        campos.put("listaCtaCostoIndirecto", listaCtaCostoIndirecto);
        campos.put("listaCtaCostoProductoTerminado", listaCtaCostoProductoTerminado);
        campos.put("listaCtaCostoTransferencia", listaCtaCostoTransferencia);
        campos.put("listaCtaCostoVenta", listaCtaCostoVenta);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudPiscina(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        PrdPiscinaPK piscinaPk = UtilsJSON.jsonToObjeto(PrdPiscinaPK.class, map.get("prdPiscinaPK"));
        List<ConEstructuraTO> listaConEstructuraTO = estructuraService.getListaConEstructuraTO(empresa);
        List<PrdPiscinaGrupoTO> listaPiscinaGrupo = piscinaGrupoService.listarPiscinaGrupoTO(empresa, false);
        List<PrdPiscinaTipo> listaPiscinaTipo = piscinaTipoService.listarPiscinaTipo(empresa, false);

        if (piscinaPk != null) {
            List<PrdPiscinaGrupoRelacionTO> listadoRelacionGrupoTO = piscinaGrupoRelacionService.listarPiscinaGrupoRelacionTO(null, piscinaPk);
            campos.put("listadoRelacionGrupoTO", listadoRelacionGrupoTO);
        }

        campos.put("listaPiscinaTipo", listaPiscinaTipo);
        campos.put("listaPiscinaGrupoTO", listaPiscinaGrupo);
        campos.put("listaConEstructuraTO", listaConEstructuraTO);
        return campos;
    }

    @Override
    public Map<String, Object> verificarExistenciaPiscina(String empresa, List<PrdPiscinaTO> importarPiscinas) throws Exception {
        Map<String, Object> mapResultadoEnviar = null;
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<PrdPiscinaTO> listaPiscina = new ArrayList();
        if (importarPiscinas != null && !importarPiscinas.isEmpty()) {
            for (int i = 0; i < importarPiscinas.size(); i++) {
                Boolean permitirRegistro = true;
                if (importarPiscinas.get(i).getCtaCostoDirecto() != null) {
                    ConCuentasTO existeCuentaCD = cuentasService.obtenerConCuentaTO(empresa, importarPiscinas.get(i).getCtaCostoDirecto());
                    if (existeCuentaCD == null) {
                        listaMensajesEnviar.add("FEl codigo de cuenta de costo directo => : <strong class='pl-2'>"
                                + importarPiscinas.get(i).getCtaCostoDirecto()
                                + " </strong> no existe.<br>");
                        permitirRegistro = false;
                    }
                }
                if (importarPiscinas.get(i).getCtaCostoIndirecto() != null) {
                    ConCuentasTO existeCuentaCI = cuentasService.obtenerConCuentaTO(empresa, importarPiscinas.get(i).getCtaCostoIndirecto());
                    if (existeCuentaCI == null) {
                        listaMensajesEnviar.add("FEl codigo de cuenta de costo indirecto => : <strong class='pl-2'>"
                                + importarPiscinas.get(i).getCtaCostoIndirecto()
                                + " </strong> no existe.<br>");
                        permitirRegistro = false;
                    }
                }
                if (importarPiscinas.get(i).getCtaCostoProductoTerminado() != null) {
                    ConCuentasTO existeCuentaCPT = cuentasService.obtenerConCuentaTO(empresa, importarPiscinas.get(i).getCtaCostoProductoTerminado());
                    if (existeCuentaCPT == null) {
                        listaMensajesEnviar.add("FEl codigo de cuenta de costo producto terminado => : <strong class='pl-2'>"
                                + importarPiscinas.get(i).getCtaCostoProductoTerminado()
                                + " </strong> no existe.<br>");
                        permitirRegistro = false;
                    }
                }
                if (importarPiscinas.get(i).getCtaCostoTransferencia() != null) {
                    ConCuentasTO existeCuentaCT = cuentasService.obtenerConCuentaTO(empresa, importarPiscinas.get(i).getCtaCostoTransferencia());
                    if (existeCuentaCT == null) {
                        listaMensajesEnviar.add("FEl codigo de cuenta de costo transferencia => : <strong class='pl-2'>"
                                + importarPiscinas.get(i).getCtaCostoTransferencia()
                                + " </strong> no existe.<br>");
                        permitirRegistro = false;
                    }
                }
                if (importarPiscinas.get(i).getCtaCostoVenta() != null) {
                    ConCuentasTO existeCuentaCV = cuentasService.obtenerConCuentaTO(empresa, importarPiscinas.get(i).getCtaCostoVenta());
                    if (existeCuentaCV == null) {
                        listaMensajesEnviar.add("FEl codigo de cuenta de costos venta => : <strong class='pl-2'>"
                                + importarPiscinas.get(i).getCtaCostoVenta()
                                + " </strong> no existe.<br>");
                        permitirRegistro = false;
                    }
                }
                if (importarPiscinas.get(i).getPisSector() != null) {
                    PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(importarPiscinas.get(i).getSecEmpresa(), importarPiscinas.get(i).getSecCodigo()));
                    if (prdSector == null) {
                        listaMensajesEnviar.add("FEl sector " + importarPiscinas.get(i).getSecCodigo() + " de la piscina " + importarPiscinas.get(i).getPisNombre() + " no existe.\nIntente ingresando otro Código...");
                        permitirRegistro = false;
                    }
                }

                if (permitirRegistro) {
                    listaPiscina.add(importarPiscinas.get(i));
                }
            }
            mapResultadoEnviar = new HashMap<String, Object>();
            mapResultadoEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
            mapResultadoEnviar.put("listaImportarPiscina", listaPiscina);
        }
        return mapResultadoEnviar;
    }

    @Override
    public Map<String, Object> insertarPiscinaImportacion(PrdPiscinaTO prdPiscinaTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean insertar = true;
        Map<String, Object> respuesta = new HashMap<>();
        PrdPiscina prdPiscina = piscinaDao.obtenerPorId(PrdPiscina.class, new PrdPiscinaPK(prdPiscinaTO.getPisEmpresa(), prdPiscinaTO.getPisSector(), prdPiscinaTO.getPisNumero()));
        if (prdPiscina != null) {
            insertar = false;
        }
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(prdPiscinaTO.getSecEmpresa(), prdPiscinaTO.getSecCodigo()));
        // CREAR EL SUCESO
        susClave = prdPiscinaTO.getPisNombre() + " - " + prdPiscinaTO.getPisSector();
        susDetalle = "Se " + (insertar ? "ingresó" : "modificó") + " la piscina " + prdPiscinaTO.getPisNombre() + ", del sector con codigo " + prdPiscinaTO.getPisSector();
        susSuceso = (insertar ? "INSERT" : "UPDATE");
        susTabla = "produccion.prd_piscina";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdPiscinaTO.setUsrFechaInsertaPiscina(UtilsValidacion.fechaSistema());
        prdPiscina = ConversionesProduccion.convertirPrdPiscinaTO_PrdPiscina(prdPiscinaTO);
        prdPiscina.setPrdSector(prdSector);

        if (insertar) {
            if (piscinaDao.insertarPrdPiscina(prdPiscina, sisSuceso)) {
                retorno = "TLa piscina: " + prdPiscinaTO.getPisNombre() + ", se ha guardado correctamente.";
            } else {
                retorno = "FLa piscina: " + prdPiscinaTO.getPisNombre() + ", NO se ha guardado correctamente.";
            }
        } else {
            if (piscinaDao.modificarPrdPiscina(prdPiscina, sisSuceso)) {
                retorno = "TLa piscina: " + prdPiscinaTO.getPisNombre() + ", se ha modificado correctamente.";
            } else {
                retorno = "TLa piscina: " + prdPiscinaTO.getPisNombre() + ", No se ha modificado correctamente.";;
            }
        }
        respuesta.put("mensaje", retorno);
        respuesta.put("piscinaTO", prdPiscinaTO);

        return respuesta;
    }

}
