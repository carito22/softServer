/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.criteria;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dev-out-04
 * @param <Entidad>
 * @param <TipoLlave>
 */
@Transactional
public interface GenericoDao<Entidad, TipoLlave> {

    Entidad insertar(Entidad entidad);

    Entidad actualizar(Entidad entidad);

    void actualizarLista(List<Entidad> listaEntidad);

    void eliminar(Entidad entidad);

    List proyeccionPorCriteria(Criterio filtro, Class resultado);

    Entidad obtener(Class<Entidad> claseEntidad, TipoLlave id);

    Boolean existe(Class<Entidad> claseEntidad, TipoLlave id);

    Entidad obtenerPorAtributo(Class<Entidad> claseEntidad, String nameColum, String valor);

    public List<Entidad> listarPorCriteriaProyeccion(Criterio filtro);

    Long cantidadPorCriteria(Criterio filtro, String distinctProperty);

    List<Entidad> listarTodosVigentes(Class<Entidad> claseEntidad, String nameColum, boolean valor);

    List<Entidad> listarPorCriteriaSinProyecciones(Criterio filtro);

    List<Entidad> listarPorCriteriaConProyecciones(Criterio filtro);

    List<Entidad> listarPorCriteriaSinProyeccionesDistinct(Criterio filtro);

    Entidad obtenerPorCriteriaSinProyeccionesDistinct(Criterio filtro);

    List<Entidad> buscarPorCriteriaSinProyecciones(Criterio filtro);

    Entidad obtenerLocal(Class<Entidad> aClass, TipoLlave id);

}
