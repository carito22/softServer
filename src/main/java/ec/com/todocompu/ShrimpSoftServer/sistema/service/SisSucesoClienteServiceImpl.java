/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoClienteDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;

/**
 *
 * @author Trabajo
 */
@Service
public class SisSucesoClienteServiceImpl implements SisSucesoClienteService {

    @Autowired
    private SucesoClienteDao sucesoClienteDao;

    @Override
    public List<SisSucesoCliente> getListaSisSucesoCliente(Integer suceso) throws Exception {
        List<SisSucesoCliente> sisSucesos = sucesoClienteDao.getListaSisSucesoCliente(suceso);

        return sisSucesos;
    }

    @Override
    public List<SisSucesoCliente> getListaSisSucesoCliente(String empresa, String cliCodigo) throws Exception {

        List<SisSucesoCliente> sucesos = sucesoClienteDao.getListaSisSucesoCliente(empresa, cliCodigo);
        for (SisSucesoCliente item : sucesos) {
            String jsonAnt = item.getSusJson();
            InvCliente clienteModificado = UtilsJSON.jsonToObjeto(InvCliente.class, jsonAnt);
            item.setInvClienteModificado(clienteModificado);
        }
        return sucesos;
    }

}
