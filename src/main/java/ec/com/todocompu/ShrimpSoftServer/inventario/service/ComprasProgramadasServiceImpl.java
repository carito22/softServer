package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.ExcepcionServicio;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasProgramadasDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasProgramadas;
import ec.com.todocompu.ShrimpSoftUtils.quartz.TO.InvComprasProgramadasTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Service
public class ComprasProgramadasServiceImpl implements ComprasProgramadasService {

    @Autowired
    private ComprasProgramadasDao comprasProgramadasDao;
    @Autowired
    private EnviarCorreoService envioCorreoService;

    @Override
    public List<InvComprasProgramadasTO> invListadoComprasProgramadasTO(String fecha) throws Exception {
        return comprasProgramadasDao.invListadoComprasProgramadasTO(fecha);
    }

    @Override
    public List<InvComprasProgramadasTO> listarInvComprasProgramadasTO(String empresa) throws Exception {
        return comprasProgramadasDao.listarInvComprasProgramadasTO(empresa);
    }

    @Override
    public boolean generarInvComprasProgramadas(InvComprasProgramadasTO invComprasProgramadas, String fecha) {
        String resultado;
        SisInfoTO sisInfoTO = new SisInfoTO(invComprasProgramadas.getCompEmpresa(), "QUARTZ", "QUARTZ", "", "WEB");
        try {
            resultado = comprasProgramadasDao.generarInvComprasProgramadas(invComprasProgramadas, fecha, sisInfoTO.getUsuario());
            if (resultado == null || resultado.charAt(0) != '-') {
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(new ExcepcionServicio("Error Controlado"), getClass().getName(), resultado, sisInfoTO));
            } else {
                return true;
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @Override
    public InvComprasProgramadas configurarCompraProgramada(String empresa, String numeroCompra, InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception {
        List<String> pks = UtilsValidacion.separar(numeroCompra, "|");
        InvCompras compra = new InvCompras(empresa, pks.get(0), pks.get(1), pks.get(2));
        comprasProgramadas.setInvCompras(compra);
        return comprasProgramadasDao.configurarCompraProgramada(comprasProgramadas, sisInfoTO);
    }

    @Override
    public InvComprasProgramadas editarConfiguracionCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception {
        return comprasProgramadasDao.editarConfiguracionCompraProgramada(comprasProgramadas, sisInfoTO);
    }

    @Override
    public InvComprasProgramadas eliminarConfiguracionCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception {
        return comprasProgramadasDao.eliminarConfiguracionCompraProgramada(comprasProgramadas, sisInfoTO);
    }

}
