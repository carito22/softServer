/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.service;

import ec.com.todocompu.ShrimpSoftServer.isp.dao.SisPromesaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPromesaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class SisPromesaPagoServiceImpl implements SisPromesaPagoService {

    @Autowired
    private SisPromesaPagoDao sisPromesaPagoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<SisPromesaPago> getListSisPromesaPagoPorcliente(String empresa, String cliCodigo) throws Exception {
        return sisPromesaPagoDao.getListSisPromesaPagoPorcliente(empresa, cliCodigo);
    }

    @Override
    public SisPromesaPago insertarSisPromesaPago(SisPromesaPago promesa, SisInfoTO sisInfoTO) throws Exception {

        promesa.setUsrEmpresa(sisInfoTO.getEmpresa());
        promesa.setUsrCodigo(sisInfoTO.getUsuario());
        promesa.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
        susClave = promesa.getProSecuencial() + " | " + promesa.getUsrEmpresa();
        susDetalle = "Se ingresó la promesa de pago: " + susClave;
        susSuceso = "INSERT";
        susTabla = "sistemaweb.sis_promesa_pago";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        promesa = sisPromesaPagoDao.insertarSisPromesaPago(promesa, sisSuceso);
        return promesa;
    }

    @Override
    public SisPromesaPago modificarSisPromesaPago(SisPromesaPago promesa, SisInfoTO sisInfoTO) throws Exception {
        susClave = promesa.getProSecuencial() + " | " + promesa.getUsrEmpresa();
        susDetalle = "Se modificó la promesa de pago: " + susClave;
        susSuceso = "UPDATE";
        susTabla = "sistemaweb.sis_promesa_pago";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        promesa = sisPromesaPagoDao.modificarSisPromesaPago(promesa, sisSuceso);
        return promesa;
    }

    @Override
    public boolean eliminarSisPromesaPago(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        susClave = "" + secuencial;
        susDetalle = "Se eliminó la promesa de pago: " + susClave;
        susSuceso = "DELETE";
        susTabla = "sistemaweb.sis_promesa_pago";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return sisPromesaPagoDao.eliminarSisPromesaPago(new SisPromesaPago(secuencial), sisSuceso);

    }

}
