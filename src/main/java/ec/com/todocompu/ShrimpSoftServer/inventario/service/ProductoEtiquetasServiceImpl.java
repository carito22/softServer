/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

/**
 *
 * @author Developer4
 */
@Service
public class ProductoEtiquetasServiceImpl implements ProductoEtiquetasService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<InvProductoEtiquetas, String> productoEtiquetaDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public InvProductoEtiquetas insertarInvProductoEtiquetas(InvProductoEtiquetas invProductoEtiquetas, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (invProductoEtiquetas.getEPrecio01()== null || invProductoEtiquetas.getEPrecio01().trim().length() == 0) {
            throw new GeneralException("El precio 1 es obligatorio.", "Datos inválidos");
        }
        if (invProductoEtiquetas.getECosto01() == null || invProductoEtiquetas.getECosto01().trim().length() == 0) {
            throw new GeneralException("El costo 1 es obligatorio.", "Datos inválidos");
        }
        susClave = invProductoEtiquetas.getEEmpresa();
        susDetalle = "Se insertó etiquetas de producto con código " + susClave;
        susSuceso = "INSERT";
        susTabla = "inventario.inv_producto_etiquetas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        invProductoEtiquetas = productoEtiquetaDao.actualizar(invProductoEtiquetas);
        sucesoDao.insertar(sisSuceso);
        return invProductoEtiquetas;
    }

    @Override
    public InvProductoEtiquetas obtenerInvProductoEtiquetas(String eEmpresa) throws GeneralException {
        return productoEtiquetaDao.obtener(InvProductoEtiquetas.class, eEmpresa);
    }
}
