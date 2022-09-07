/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Developer4
 */
@Transactional
public interface ProductoEtiquetasService {

    public InvProductoEtiquetas insertarInvProductoEtiquetas(InvProductoEtiquetas invProductoEtiquetas, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public InvProductoEtiquetas obtenerInvProductoEtiquetas(String eEmpresa) throws GeneralException;
}
