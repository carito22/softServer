package ec.com.todocompu.ShrimpSoftServer.caja.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoComprobanteService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.caja.dao.CajaDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasFormaCobroService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.FirmaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCaja;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCaja;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.guardarArchivo;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CajaServiceImpl implements CajaService {

    @Autowired
    private CajaDao cajaDao;
    @Autowired
    private VentasMotivoService ventasMotivoService;
    @Autowired
    private TipoComprobanteService tipoComprobanteService;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private VentasFormaCobroService ventaFormaCobroService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    @Autowired
    private FirmaElectronicaService firmaElectronicaService;

    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public Map<String, Object> getDatosPerfilFacturacion(String empresa, CajCajaPK cajCajaPK, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        Map<String, Object> campos = new HashMap<>();
        List<SisListaUsuarioTO> listaSisListaUsuarioTO = getListaUsuariosDisponibles(empresa, sisInfoTO);
        List<InvVentaMotivoComboTO> listaInvVentaMotivoComboTO = ventasMotivoService.getListaVentaMotivoComboTO(empresa, false);
        List<AnxTipoComprobanteComboTO> listaAnxTipoComprobanteComboTO = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(null);
        List<InvComboBodegaTO> listaInvComboBodegaTO = bodegaService.getInvComboBodegaTO(empresa);
        List<InvVentasFormaCobroTO> listaInvVentasFormaCobroTO = ventaFormaCobroService.getListaInvVentasFormaCobroTO(empresa, false);
        InvProductoEtiquetas invProductoEtiquetas = productoService.traerEtiquetas(empresa);
        List<SisFirmaElectronica> firmas = firmaElectronicaService.listarSisFirmaElectronica(empresa);
        if (cajCajaPK != null) {
            CajCajaTO cajCajaTO = getCajCajaTO(cajCajaPK.getCajaEmpresa(), cajCajaPK.getCajaUsuarioResponsable());
            campos.put("cajCajaTO", cajCajaTO);
        }
        campos.put("listaSisListaUsuarioTO", listaSisListaUsuarioTO);
        campos.put("listaInvVentaMotivoComboTO", listaInvVentaMotivoComboTO);
        campos.put("listaAnxTipoComprobanteComboTO", listaAnxTipoComprobanteComboTO);
        campos.put("listaInvComboBodegaTO", listaInvComboBodegaTO);
        campos.put("listaInvVentasFormaCobroTO", listaInvVentasFormaCobroTO);
        campos.put("invProductoEtiquetas", invProductoEtiquetas);
        campos.put("firmas", firmas);
        return campos;
    }

    @Override
    public List<SisListaUsuarioTO> getListaUsuariosDisponibles(String empresa, SisInfoTO sisInfoTO) throws Exception {
        List<SisListaUsuarioTO> listaSisListaUsuarioTOEliminar = new ArrayList<>();
        List<SisListaUsuarioTO> listaSisListaUsuarioTO = usuarioDetalleService.getListaSisUsuarios(empresa);
        List<CajCajaTO> listaCajCajaTO = getListadoCajCajaTO(empresa);
        for (SisListaUsuarioTO sisListaUsuarioTO : listaSisListaUsuarioTO) {
            for (CajCajaTO cajaCajTO : listaCajCajaTO) {
                if (sisListaUsuarioTO.getUsrCodigo().equalsIgnoreCase(cajaCajTO.getCajaUsuarioResponsable())) {
                    listaSisListaUsuarioTOEliminar.add(sisListaUsuarioTO);
                    break;
                }
            }
        }
        listaSisListaUsuarioTO.removeAll(listaSisListaUsuarioTOEliminar);
        return listaSisListaUsuarioTO;
    }

    @Override
    public MensajeTO accionCajCajaTO(CajCajaTO cajCajaTO, String accion, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        CajCaja cajCaja = null;
        CajCaja cajCajaAux = null;
        cajCajaAux = cajaDao.obtenerPorId(CajCaja.class, new CajCajaPK(cajCajaTO.getCajaEmpresa(), cajCajaTO.getCajaUsuarioResponsable()));
        if (accion.equals("I") && cajCajaAux == null || accion.equals("U") && cajCajaAux != null
                || accion.equals("D") && cajCajaAux != null) {
            cajCajaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            cajCaja = ConversionesCaja.convertirCajCajaTO_CajCaja(cajCajaTO);

            susClave = cajCajaTO.getCajaUsuarioResponsable();
            if (accion.equals("I")) {
                susDetalle = "Se ingresó la Caja " + cajCajaTO.getCajaUsuarioResponsable();
                susSuceso = "INSERT";
                mensaje = "T<html>Se guardó la información correctamente.</html>";
            } else if (accion.equals("U")) {
                susDetalle = "Se modificó la Caja " + cajCajaTO.getCajaUsuarioResponsable();
                susSuceso = "UPDATE";
                mensaje = "T<html>Se actualizó la información correctamente.</html>";
            } else if (accion.equals("D")) {
                susDetalle = "Se eliminó la Caja " + cajCajaTO.getCajaUsuarioResponsable();
                susSuceso = "DELETE";
                mensaje = "T<html>Se eliminó la información correctamente.</html>";
            }
            susTabla = "caja.caj_caja";

            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            cajaDao.accionCajCaja(cajCajaAux, cajCaja, sisSuceso, accion);
        } else if (accion.equals("I") && cajCajaAux != null) {
            mensaje = "F<html>La caja que va a ingresar ya existe.<br>Intente con otro.</html>";
        } else if (accion.equals("U") && cajCajaAux == null || accion.equals("D") && cajCajaAux == null) {
            mensaje = "F<html>La caja que va a " + (accion.equals("U") ? "modificar" : "eliminar")
                    + "ya no existe.<br>Intente con otro.";
        }
        mensajeTO.setMensaje(mensaje);
        return mensajeTO;
    }

    @Override
    public List<CajCajaTO> getListadoCajCajaTO(String empresa) throws Exception {
        return cajaDao.getListadoCajCajaTO(empresa);
    }

    @Override
    public List<CajCajaComboTO> getCajCajaComboTO(String empresa) throws Exception {
        return cajaDao.getCajCajaComboTO(empresa);
    }

    @Override
    public CajCajaTO getCajCajaTO(String empresa, String usuarioCodigo) throws Exception {
        return cajaDao.getCajCajaTO(empresa, usuarioCodigo);
    }

    @Override
    public String guardarArchivoFirmaElectronica(byte[] imagen, String nombre) throws Exception {
        return guardarArchivo(imagen, nombre);
    }

}
