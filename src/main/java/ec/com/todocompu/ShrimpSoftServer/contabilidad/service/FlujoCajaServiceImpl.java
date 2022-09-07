/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.FlujoCajaDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaChequesPostfechadosProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorCobrarClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorPagarProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaValorizacionActivoBiologicoTO;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mario
 */
@Service
public class FlujoCajaServiceImpl implements FlujoCajaService {

    @Autowired
    private FlujoCajaDao flujoCajaDao;

    @Override
    public Map<String, Object> obtenerProyectoFlujoCaja(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));

        BigDecimal sumaBanco = BigDecimal.ZERO;
        BigDecimal sumaClientes = BigDecimal.ZERO;
        BigDecimal sumaProveedores = BigDecimal.ZERO;
        BigDecimal sumaCheque = BigDecimal.ZERO;
        BigDecimal sumaValorizacion = BigDecimal.ZERO;
        BigDecimal sumaEfectivo = BigDecimal.ZERO;//banco + clientes
        BigDecimal sumaCuentasPorPagar = BigDecimal.ZERO;//proveedores + cheques
        BigDecimal subtotal = BigDecimal.ZERO;//efectivo + cuentas por pagar
        BigDecimal total = BigDecimal.ZERO;//subtotal + valorizacion

        List<ConFlujoCajaTO> listaFlujoCaja = flujoCajaDao.listarFlujoDeCaja(empresa, fechaHasta);
        if (listaFlujoCaja != null && !listaFlujoCaja.isEmpty()) {
            sumaBanco = listaFlujoCaja.stream()
                    .map(ConFlujoCajaTO::getFcSaldo).filter(Objects::nonNull).filter(i -> (i.toBigInteger() != null)).reduce(BigDecimal.ZERO, BigDecimal::add);
            campos.put("sumaBanco", sumaBanco);
        }
        List<ConFlujoCajaCuentasPorCobrarClientesTO> listaCuentasPorCobrarClientes = flujoCajaDao.listarCuentasPorCobrarClientes(empresa, fechaHasta);
        if (listaCuentasPorCobrarClientes != null && !listaCuentasPorCobrarClientes.isEmpty()) {
            sumaClientes = listaCuentasPorCobrarClientes.stream()
                    .map(ConFlujoCajaCuentasPorCobrarClientesTO::getCxcdSaldo).filter(Objects::nonNull).filter(i -> (i.toBigInteger() != null)).reduce(BigDecimal.ZERO, BigDecimal::add);
            campos.put("sumaClientes", sumaClientes);
        }
        List<ConFlujoCajaCuentasPorPagarProveedoresTO> listaCuentasPorPagarProveedores = flujoCajaDao.listarCuentasPorPagarProveedores(empresa, fechaHasta);
        if (listaCuentasPorPagarProveedores != null && !listaCuentasPorPagarProveedores.isEmpty()) {
            sumaProveedores = listaCuentasPorPagarProveedores.stream()
                    .map(ConFlujoCajaCuentasPorPagarProveedoresTO::getCxpdSaldo).filter(Objects::nonNull).filter(i -> (i.toBigInteger() != null)).reduce(BigDecimal.ZERO, BigDecimal::add);
            campos.put("sumaProveedores", sumaProveedores);
        }
        List<ConFlujoCajaChequesPostfechadosProveedoresTO> listaChequesPostfechados = flujoCajaDao.listarChequesPosfechados(empresa, fechaHasta);
        if (listaChequesPostfechados != null && !listaChequesPostfechados.isEmpty()) {
            sumaCheque = listaChequesPostfechados.stream()
                    .map(ConFlujoCajaChequesPostfechadosProveedoresTO::getChqValor).filter(Objects::nonNull).filter(i -> (i.toBigInteger() != null)).reduce(BigDecimal.ZERO, BigDecimal::add);
            campos.put("sumaCheque", sumaCheque);
        }
        List<ConFlujoCajaValorizacionActivoBiologicoTO> listaValorizacion = flujoCajaDao.getListaBuscarConCuentasFlujoDetalleTO(empresa, fechaHasta);
        if (listaValorizacion != null && !listaValorizacion.isEmpty()) {
            sumaValorizacion = listaValorizacion.stream()
                    .map(ConFlujoCajaValorizacionActivoBiologicoTO::getFcVenta).filter(Objects::nonNull).filter(i -> (i.toBigInteger() != null)).reduce(BigDecimal.ZERO, BigDecimal::add);
            campos.put("sumaValorizacion", sumaValorizacion);
        }

        campos.put("listaFlujoCaja", listaFlujoCaja);
        campos.put("listaCuentasPorCobrarClientes", listaCuentasPorCobrarClientes);
        campos.put("listaCuentasPorPagarProveedores", listaCuentasPorPagarProveedores);
        campos.put("listaChequesPostfechados", listaChequesPostfechados);
        campos.put("listaValorizacion", listaValorizacion);
        campos.put("sumaBanco", sumaBanco);
        campos.put("sumaClientes", sumaClientes);
        campos.put("sumaProveedores", sumaProveedores);
        campos.put("sumaCheque", sumaCheque);
        campos.put("sumaValorizacion", sumaValorizacion);

        sumaEfectivo = sumaBanco.add(sumaClientes);
        campos.put("sumaEfectivo", sumaEfectivo);
        sumaCuentasPorPagar = sumaProveedores;
        campos.put("sumaCuentasPorPagar", sumaCuentasPorPagar);
        subtotal = sumaEfectivo.subtract(sumaCuentasPorPagar);
        campos.put("subtotal", subtotal);
        total = subtotal.add(sumaValorizacion);
        campos.put("total", total);

        return campos;
    }

}
