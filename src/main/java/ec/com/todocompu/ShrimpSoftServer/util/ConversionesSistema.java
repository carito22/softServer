package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisSucesoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPcs;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;

public class ConversionesSistema {

    public static SisEmpresa ConvertirSisEmpresaTO_SisEmpresa(SisEmpresaTO sisEmpresaTO) {
        SisEmpresa sisEmpresa = new SisEmpresa();
        sisEmpresa.setEmpCodigo(sisEmpresaTO.getEmpCodigo());
        sisEmpresa.setEmpRuc(sisEmpresaTO.getEmpRuc());
        sisEmpresa.setEmpNombre(sisEmpresaTO.getEmpNombre());
        sisEmpresa.setEmpRazonSocial(sisEmpresaTO.getEmpRazonSocial());
        sisEmpresa.setEmpDireccion(sisEmpresaTO.getEmpDireccion());
        sisEmpresa.setEmpCiudad(sisEmpresaTO.getEmpCiudad());
        sisEmpresa.setEmpPais(sisEmpresaTO.getEmpPais());
        sisEmpresa.setEmpTelefono(sisEmpresaTO.getEmpTelefono());
        sisEmpresa.setEmpCelular(sisEmpresaTO.getEmpCelular());
        sisEmpresa.setEmpEmail(sisEmpresaTO.getEmpEmail());
        sisEmpresa.setEmpClave(sisEmpresaTO.getEmpClave());
        sisEmpresa.setEmpActiva(sisEmpresaTO.getEmpActiva());
        sisEmpresa.setUsrCodigo(sisEmpresaTO.getUsrInsertaUsuario());
        sisEmpresa.setUsrFechaInsertaEmpresa(sisEmpresaTO.getUsrFechaInsertaUsuario());
        return sisEmpresa;
    }

    public static SisEmpresaTO ConvertirSisEmpresa_SisEmpresaTO(SisEmpresa sisEmpresa) {
        SisEmpresaTO sisEmpresaTO = new SisEmpresaTO();
        sisEmpresaTO.setEmpCodigo(sisEmpresa.getEmpCodigo());
        sisEmpresaTO.setEmpRuc(sisEmpresa.getEmpRuc());
        sisEmpresaTO.setEmpNombre(sisEmpresa.getEmpNombre());
        sisEmpresaTO.setEmpRazonSocial(sisEmpresa.getEmpRazonSocial());
        sisEmpresaTO.setEmpDireccion(sisEmpresa.getEmpDireccion());
        sisEmpresaTO.setEmpCiudad(sisEmpresa.getEmpCiudad());
        sisEmpresaTO.setEmpPais(sisEmpresa.getEmpPais());
        sisEmpresaTO.setEmpTelefono(sisEmpresa.getEmpTelefono());
        sisEmpresaTO.setEmpCelular(sisEmpresa.getEmpCelular());
        sisEmpresaTO.setEmpEmail(sisEmpresa.getEmpEmail());
        sisEmpresaTO.setEmpClave(sisEmpresa.getEmpClave());
        sisEmpresaTO.setEmpActiva(sisEmpresa.getEmpActiva());
        sisEmpresaTO.setUsrInsertaUsuario(sisEmpresa.getUsrCodigo());
        sisEmpresaTO.setUsrFechaInsertaUsuario(sisEmpresa.getUsrFechaInsertaEmpresa());
        return sisEmpresaTO;
    }

    public static SisSuceso ConvertirSisSucesoTO_SisSuceso(SisSucesoTO sisSucesoTO) {
        SisSuceso sisSuceso = new SisSuceso();
        sisSuceso.setSusSecuencia(sisSucesoTO.getSusSecuencia());
        sisSuceso.setSusTabla(sisSucesoTO.getSusTabla());
        sisSuceso.setSusClave(sisSucesoTO.getSusClave());
        sisSuceso.setSusSuceso(sisSucesoTO.getSusSuceso());
        sisSuceso.setSusDetalle(sisSucesoTO.getSusDetalle());
        sisSuceso.setDetEmpresa(sisSucesoTO.getUsrEmpresa());
        sisSuceso.setSisUsuario(new SisUsuario(sisSucesoTO.getUsrCodigo()));
        sisSuceso.setSusFecha(UtilsDate.timestamp(sisSucesoTO.getSusFecha()));
        return sisSuceso;
    }

    public static SisPcs ConvertirSisPcs_SisPcs(SisPcs sisPcs) {
        SisPcs sisPcsAux = new SisPcs();
        sisPcsAux.setInfMac(sisPcs.getInfMac());
        sisPcsAux.setInfNombre(sisPcs.getInfNombre());
        sisPcsAux.setInfDescripcion(sisPcs.getInfDescripcion());
        sisPcsAux.setInfEstado(sisPcs.getInfEstado());
        sisPcsAux.setUsrCodigo(sisPcs.getUsrCodigo());
        sisPcsAux.setUsrFechaInsertaPc(sisPcs.getUsrFechaInsertaPc());
        return sisPcsAux;
    }

    public static SisUsuario ConvertirSisUsuarioTO_SisUsuario(SisUsuarioTO sisUsuarioTO) {
        SisUsuario sisUsuario = new SisUsuario();
        sisUsuario.setUsrCodigo(sisUsuarioTO.getUsrCodigo());
        sisUsuario.setUsrNick(sisUsuarioTO.getUsrNick());
        sisUsuario.setUsrNombre(sisUsuarioTO.getUsrNombre());
        sisUsuario.setUsrApellido(sisUsuarioTO.getUsrApellido());
        sisUsuario.setUsrPassword(sisUsuarioTO.getUsrPassword());
        sisUsuario.setUsrCaduca(UtilsValidacion.fechaString_Date(sisUsuarioTO.getUsrCaduca(), "yyyy-MM-dd"));
        sisUsuario.setUsrActivo(sisUsuarioTO.getUsrActivo());

        sisUsuario.setUsrEmail(sisUsuarioTO.getUsrEmail());
        sisUsuario.setUsrPasswordEmail(sisUsuarioTO.getUsrPasswordEmail());

        sisUsuario.setUsrCambiarContrasenia(sisUsuarioTO.getUsrCambiarContrasenia());
        sisUsuario.setUsrCodigoInserta(sisUsuarioTO.getUsrInsertaUsuario());
        sisUsuario.setUsrFechaInsertaUsuario(UtilsValidacion.fechaString_Date(sisUsuarioTO.getUsrFechaInsertaUsuario()));
        sisUsuario.setUsrTelefono(sisUsuarioTO.getUsrTelefono());

        return sisUsuario;
    }

    public static SisPeriodo ConvertirSisPeriodoTO_SisPeriodo(SisPeriodoTO sisPeriodoTO) {
        SisPeriodo sisPeriodo = new SisPeriodo();
        sisPeriodo.setSisPeriodoPK(new SisPeriodoPK(sisPeriodoTO.getPerEmpresa(), sisPeriodoTO.getPerCodigo()));
        sisPeriodo.setPerDetalle(sisPeriodoTO.getPerDetalle());
        sisPeriodo.setPerDesde(UtilsValidacion.fecha(sisPeriodoTO.getPerDesde(), "yyyy-MM-dd"));
        sisPeriodo.setPerHasta(UtilsValidacion.fecha(sisPeriodoTO.getPerHasta(), "yyyy-MM-dd"));
        sisPeriodo.setPerCerrado(sisPeriodoTO.getPerCerrado());
        sisPeriodo.setEmpCodigo(new SisEmpresa(sisPeriodoTO.getEmpCodigo()));
        sisPeriodo.setUsrCodigo(sisPeriodoTO.getUsrInsertaPeriodo());
        sisPeriodo.setUsrFechaInsertaPeriodo(UtilsValidacion.fechaString_Date(sisPeriodoTO.getUsrFechaInsertaPeriodo()));
        return sisPeriodo;
    }

    public static SisEmailComprobanteElectronicoTO completarDatosComprobanteElectronicoTO(SisEmpresaParametros sisEmpresaParametros) {
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
        sisEmailComprobanteElectronicoTO.setRucEmisor(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
        sisEmailComprobanteElectronicoTO.setNombreEmisor(sisEmpresaParametros.getEmpCodigo().getEmpNombre());
        sisEmailComprobanteElectronicoTO.setTelefonoEmisor(sisEmpresaParametros.getEmpCodigo().getEmpTelefono());
        sisEmailComprobanteElectronicoTO.setDireccionEmisor(sisEmpresaParametros.getEmpCodigo().getEmpDireccion());
        sisEmailComprobanteElectronicoTO.setUrlWebDocumentoElectronico(sisEmpresaParametros.getParWebDocumentosElectronicos());
        sisEmailComprobanteElectronicoTO.setNombreReceptor(sisEmpresaParametros.getEmpCodigo().getEmpNombre());
        return sisEmailComprobanteElectronicoTO;
    }

    public static SisNotificacion completarDatosSisNotificacion(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO) {
        SisNotificacion sisNotificacion = new SisNotificacion();

        sisNotificacion.setMailEmisor(sisEmailComprobanteElectronicoTO.getMailEmisor());
        sisNotificacion.setMailPasswordEmisor(sisEmailComprobanteElectronicoTO.getClaveEmisor());
        sisNotificacion.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor());
        return sisNotificacion;
    }

    public static SisGrupo ConvertirSisGrupoTO_SisGrupo(SisGrupoTO sisGrupoTO) {
        SisGrupo sisGrupo = new SisGrupo();
        sisGrupo.setSisGrupoPK(new SisGrupoPK(sisGrupoTO.getGruEmpresa(), sisGrupoTO.getGruCodigo()));
        sisGrupo.setGruNombre(sisGrupoTO.getGruNombre());

        sisGrupo.setGruCrear(sisGrupoTO.getGruCrear());
        sisGrupo.setGruCrearClientes(sisGrupoTO.getGruCrearClientes());
        sisGrupo.setGruCrearProveedores(sisGrupoTO.getGruCrearProveedores());
        sisGrupo.setGruCrearProductos(sisGrupoTO.getGruCrearProductos());
        sisGrupo.setGruCrearCuentasContables(sisGrupoTO.getGruCrearCuentasContables());
        sisGrupo.setGruCrearEmpleados(sisGrupoTO.getGruCrearEmpleados());
        sisGrupo.setGruCrearCentrosProduccion(sisGrupoTO.getGruCrearCentrosProduccion());
        sisGrupo.setGruCrearCentrosCosto(sisGrupoTO.getGruCrearCentrosCosto());
        sisGrupo.setGruCrearPeriodosSistema(sisGrupoTO.getGruCrearPeriodosSistema());
        sisGrupo.setGruCrearPeriodosProduccion(sisGrupoTO.getGruCrearPeriodosProduccion());

        sisGrupo.setGruModificar(sisGrupoTO.getGruModificar());
        sisGrupo.setGruModificarClientes(sisGrupoTO.getGruModificarClientes());
        sisGrupo.setGruModificarProveedores(sisGrupoTO.getGruModificarProveedores());
        sisGrupo.setGruModificarProductos(sisGrupoTO.getGruModificarProductos());
        sisGrupo.setGruModificarCuentasContables(sisGrupoTO.getGruModificarCuentasContables());
        sisGrupo.setGruModificarEmpleados(sisGrupoTO.getGruModificarEmpleados());
        sisGrupo.setGruModificarCentrosProduccion(sisGrupoTO.getGruModificarCentrosProduccion());
        sisGrupo.setGruModificarCentrosCosto(sisGrupoTO.getGruModificarCentrosCosto());
        sisGrupo.setGruModificarPeriodosSistema(sisGrupoTO.getGruModificarPeriodosSistema());
        sisGrupo.setGruModificarPeriodosProduccion(sisGrupoTO.getGruModificarPeriodosProduccion());
        sisGrupo.setGruModificarOcAprobadas(sisGrupoTO.getGruModificarOcAprobadas());

        sisGrupo.setGruEliminar(sisGrupoTO.getGruEliminar());
        sisGrupo.setGruEliminarCompras(sisGrupoTO.getGruEliminarCompras());
        sisGrupo.setGruEliminarVentas(sisGrupoTO.getGruEliminarVentas());
        sisGrupo.setGruEliminarConsumos(sisGrupoTO.getGruEliminarConsumos());
        sisGrupo.setGruEliminarTransferencias(sisGrupoTO.getGruEliminarTransferencias());
        sisGrupo.setGruEliminarContables(sisGrupoTO.getGruEliminarContables());
        sisGrupo.setGruEliminarContablesTalentoHumano(sisGrupoTO.getGruEliminarContablesTalentoHumano());
        sisGrupo.setGruEliminarPresupuestos(sisGrupoTO.getGruEliminarPresupuestos());
        sisGrupo.setGruEliminarPreliquidaciones(sisGrupoTO.getGruEliminarPreliquidaciones());
        sisGrupo.setGruEliminarLiquidaciones(sisGrupoTO.getGruEliminarLiquidaciones());
        sisGrupo.setGruEliminarProformas(sisGrupoTO.getGruEliminarProformas());
        sisGrupo.setGruEliminarContratos(sisGrupoTO.getGruEliminarContratos());

        sisGrupo.setGruMayorizarCompras(sisGrupoTO.getGruMayorizarCompras());
        sisGrupo.setGruMayorizarVentas(sisGrupoTO.getGruMayorizarVentas());
        sisGrupo.setGruMayorizarConsumos(sisGrupoTO.getGruMayorizarConsumos());
        sisGrupo.setGruMayorizarTransferencias(sisGrupoTO.getGruMayorizarTransferencias());
        sisGrupo.setGruMayorizarContables(sisGrupoTO.getGruMayorizarContables());
        sisGrupo.setGruMayorizarContablesTalentoHumano(sisGrupoTO.getGruMayorizarContablesTalentoHumano());
        sisGrupo.setGruMayorizarPresupuestos(sisGrupoTO.getGruMayorizarPresupuestos());
        sisGrupo.setGruMayorizarPreliquidaciones(sisGrupoTO.getGruMayorizarPreliquidaciones());
        sisGrupo.setGruMayorizarLiquidaciones(sisGrupoTO.getGruMayorizarLiquidaciones());
        sisGrupo.setGruMayorizarProformas(sisGrupoTO.getGruMayorizarProformas());

        sisGrupo.setGruDesmayorizarCompras(sisGrupoTO.getGruDesmayorizarCompras());
        sisGrupo.setGruDesmayorizarVentas(sisGrupoTO.getGruDesmayorizarVentas());
        sisGrupo.setGruDesmayorizarConsumos(sisGrupoTO.getGruDesmayorizarConsumos());
        sisGrupo.setGruDesmayorizarTransferencias(sisGrupoTO.getGruDesmayorizarTransferencias());
        sisGrupo.setGruDesmayorizarContables(sisGrupoTO.getGruDesmayorizarContables());
        sisGrupo.setGruDesmayorizarContablesTalentoHumano(sisGrupoTO.getGruDesmayorizarContablesTalentoHumano());
        sisGrupo.setGruDesmayorizarPresupuestos(sisGrupoTO.getGruDesmayorizarPresupuestos());
        sisGrupo.setGruDesmayorizarPreliquidaciones(sisGrupoTO.getGruDesmayorizarPreliquidaciones());
        sisGrupo.setGruDesmayorizarLiquidaciones(sisGrupoTO.getGruDesmayorizarLiquidaciones());
        sisGrupo.setGruDesmayorizarProformas(sisGrupoTO.getGruDesmayorizarProformas());

        sisGrupo.setGruAnularCompras(sisGrupoTO.getGruAnularCompras());
        sisGrupo.setGruAnularVentas(sisGrupoTO.getGruAnularVentas());
        sisGrupo.setGruAnularConsumos(sisGrupoTO.getGruAnularConsumos());
        sisGrupo.setGruAnularTransferencias(sisGrupoTO.getGruAnularTransferencias());
        sisGrupo.setGruAnularContables(sisGrupoTO.getGruAnularContables());
        sisGrupo.setGruAnularContablesTalentoHumano(sisGrupoTO.getGruAnularContablesTalentoHumano());
        sisGrupo.setGruAnularPresupuestos(sisGrupoTO.getGruAnularPresupuestos());
        sisGrupo.setGruAnularPreliquidaciones(sisGrupoTO.getGruAnularPreliquidaciones());
        sisGrupo.setGruAnularLiquidaciones(sisGrupoTO.getGruAnularLiquidaciones());
        sisGrupo.setGruAnularProformas(sisGrupoTO.getGruAnularProformas());

        sisGrupo.setGruConfigurar(sisGrupoTO.getGruConfigurar());
        sisGrupo.setGruImprimir(sisGrupoTO.getGruImprimir());
        sisGrupo.setGruExportar(sisGrupoTO.getGruExportar());

        sisGrupo.setGruMayorizarPagos(sisGrupoTO.getGruMayorizarPagos());
        sisGrupo.setGruMayorizarCobros(sisGrupoTO.getGruMayorizarCobros());
        sisGrupo.setGruMayorizarAnticiposClientes(sisGrupoTO.getGruMayorizarAnticiposClientes());
        sisGrupo.setGruMayorizarAnticiposProveedores(sisGrupoTO.getGruMayorizarAnticiposProveedores());
        sisGrupo.setGruDesmayorizarPagos(sisGrupoTO.getGruDesmayorizarPagos());
        sisGrupo.setGruDesmayorizarCobros(sisGrupoTO.getGruDesmayorizarCobros());
        sisGrupo.setGruDesmayorizarAnticiposClientes(sisGrupoTO.getGruDesmayorizarAnticiposClientes());
        sisGrupo.setGruDesmayorizarAnticiposProveedores(sisGrupoTO.getGruDesmayorizarAnticiposProveedores());
        sisGrupo.setGruAnularRetencion(sisGrupoTO.getGruAnularRetencion());
        sisGrupo.setGruDashboard(sisGrupoTO.getGruDashboard());
        sisGrupo.setGruReutilizarCheque(sisGrupoTO.getGruReutilizarCheque());
        sisGrupo.setGruAdjuntarImagenes(sisGrupoTO.getGruAdjuntarImagenes());
        sisGrupo.setUsrInsertaGrupo(sisGrupoTO.getUsrInsertaGrupo());
        sisGrupo.setUsrFechaInsertaGrupo(sisGrupoTO.getUsrFechaInsertaGrupo());
        
        sisGrupo.setGruConsultarAnticiposClientes(sisGrupoTO.getGruConsultarAnticiposClientes());
        sisGrupo.setGruConsultarAnticiposProveedores(sisGrupoTO.getGruConsultarAnticiposProveedores());
        sisGrupo.setGruConsultarCobros(sisGrupoTO.getGruConsultarCobros());
        sisGrupo.setGruConsultarCompras(sisGrupoTO.getGruConsultarCompras());
        sisGrupo.setGruConsultarConsumos(sisGrupoTO.getGruConsultarConsumos());
        sisGrupo.setGruConsultarContables(sisGrupoTO.getGruConsultarContables());
        sisGrupo.setGruConsultarContablesTalentoHumano(sisGrupoTO.getGruConsultarContablesTalentoHumano());
        sisGrupo.setGruConsultarLiquidaciones(sisGrupoTO.getGruConsultarLiquidaciones());
        sisGrupo.setGruConsultarPagos(sisGrupoTO.getGruConsultarPagos());
        sisGrupo.setGruConsultarPreliquidaciones(sisGrupoTO.getGruConsultarPreliquidaciones());
        sisGrupo.setGruConsultarPresupuestos(sisGrupoTO.getGruConsultarPresupuestos());
        sisGrupo.setGruConsultarProformas(sisGrupoTO.getGruConsultarProformas());
        sisGrupo.setGruConsultarTransferencias(sisGrupoTO.getGruConsultarTransferencias());
        sisGrupo.setGruConsultarVentas(sisGrupoTO.getGruConsultarVentas());
        return sisGrupo;
    }

}
