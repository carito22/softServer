/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasNumeracionPK;

@Repository
public class TransferenciasNumeracionDaoImpl
		extends GenericDaoImpl<InvTransferenciasNumeracion, InvTransferenciasNumeracionPK>
		implements TransferenciasNumeracionDao {

}
