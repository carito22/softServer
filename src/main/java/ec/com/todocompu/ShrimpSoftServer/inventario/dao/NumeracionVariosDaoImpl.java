package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;

@Repository
public class NumeracionVariosDaoImpl extends GenericDaoImpl<InvNumeracionVarios, String>
		implements NumeracionVariosDao {

}
