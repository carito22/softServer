/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDetalleImbDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleImb;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class ComprasDetalleImbServiceImpl implements ComprasDetalleImbService {

    @Autowired
    private ComprasDetalleImbDao comprasDetalleImbDao;

    @Override
    public List<InvComprasDetalleImb> getListInvComprasDetalleImb(String empresa, String periodo, String motivo, String numero) throws Exception {
        return comprasDetalleImbDao.getListInvComprasDetalleImb(empresa, periodo, motivo, numero);
    }

}
