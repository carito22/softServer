/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfActivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfCategoriasTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfUbicacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfCategorias;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciaciones;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfUbicaciones;

/**
 *
 * @author CarolValdiviezo
 */
public class ConversionesActivoFijo {

    public static AfActivoTO convertirAfActivos_AfActivoTO(AfActivos afActivos) {
        AfActivoTO afActivoTO = new AfActivoTO();
        afActivoTO.setAfCategorias(convertirAfCategorias_AfCategoriasTO(afActivos.getAfCategorias()));
        afActivoTO.setAfCodigo(afActivos.getAfActivosPK().getAfCodigo());
        afActivoTO.setAfDepreciacionInicialMeses(afActivos.getAfDepreciacionInicialMeses());
        afActivoTO.setAfDepreciacionInicialMonto(afActivos.getAfDepreciacionInicialMonto());
        afActivoTO.setAfDescripcion(afActivos.getAfDescripcion());
        afActivoTO.setAfEmpresa(afActivos.getAfActivosPK().getAfEmpresa());
        afActivoTO.setAfFechaAdquisicion(afActivos.getAfFechaAdquisicion());
        afActivoTO.setAfUbicaciones(convertirAfUbicaciones_AfUbicacionesTO(afActivos.getAfUbicaciones()));
        afActivoTO.setAfValorAdquision(afActivos.getAfValorAdquision());
        afActivoTO.setAfValorResidual(afActivos.getAfValorResidual());
        afActivoTO.setAfVidaUtil(afActivos.getAfVidaUtil());
        afActivoTO.setCategoriaDescripcion(afActivoTO.getAfCategorias().getCatDescripcion());
        afActivoTO.setUbicacionDescripcion(afActivoTO.getAfUbicaciones().getUbiDescripcion());
        afActivoTO.setUsrCodigo(afActivos.getUsrCodigo());
        afActivoTO.setUsrEmpresa(afActivos.getUsrEmpresa());
        afActivoTO.setUsrFechaInserta(afActivos.getUsrFechaInserta());
        return afActivoTO;
    }

    public static AfCategoriasTO convertirAfCategorias_AfCategoriasTO(AfCategorias afCategorias) {
        AfCategoriasTO afCategoriasTO = new AfCategoriasTO();
        afCategoriasTO.setCatCodigo(afCategorias.getAfCategoriasPK().getCatCodigo());
        afCategoriasTO.setCatCuentaActivo(afCategorias.getCatCuentaActivo());
        afCategoriasTO.setCatCuentaDepreciacion(afCategorias.getCatCuentaDepreciacionAcumulada());
        afCategoriasTO.setCatCuentaDepreciacionAcumulada(afCategorias.getCatCuentaDepreciacionAcumulada());
        afCategoriasTO.setCatDescripcion(afCategorias.getCatDescripcion());
        afCategoriasTO.setCatEmpresa(afCategorias.getAfCategoriasPK().getCatEmpresa());
        afCategoriasTO.setCatInactivo(afCategorias.getCatInactivo());
        afCategoriasTO.setCatPorcentajeDepreciacion(afCategorias.getCatPorcentajeDepreciacion());
        afCategoriasTO.setCatVidaUtil(afCategorias.getCatVidaUtil());
        afCategoriasTO.setUsrCodigo(afCategorias.getUsrCodigo());
        afCategoriasTO.setUsrEmpresa(afCategorias.getAfCategoriasPK().getCatEmpresa());
        afCategoriasTO.setUsrFechaInserta(afCategorias.getUsrFechaInserta());
        return afCategoriasTO;
    }

    public static AfUbicacionesTO convertirAfUbicaciones_AfUbicacionesTO(AfUbicaciones afUbicaciones) {
        AfUbicacionesTO afUbicacionesTO = new AfUbicacionesTO();
        afUbicacionesTO.setUbiCodigo(afUbicaciones.getAfUbicacionesPK().getUbiCodigo());
        afUbicacionesTO.setUbiDescripcion(afUbicaciones.getUbiDescripcion());
        afUbicacionesTO.setUbiEmpresa(afUbicaciones.getAfUbicacionesPK().getUbiEmpresa());
        afUbicacionesTO.setUbiSector(afUbicaciones.getAfUbicacionesPK().getUbiSector());
        afUbicacionesTO.setUsrCodigo(afUbicaciones.getUsrCodigo());
        afUbicacionesTO.setUsrEmpresa(afUbicaciones.getAfUbicacionesPK().getUbiEmpresa());
        afUbicacionesTO.setUsrFechaInserta(afUbicaciones.getUsrFechaInserta());

        return afUbicacionesTO;
    }

    public static AfDepreciaciones convertirAfDepreciacionesTO_AfDepreciaciones(AfActivos afActivos, AfDepreciacionesTO afDepreciacionesTO) {
        AfDepreciaciones afDepreciaciones = new AfDepreciaciones();
        afDepreciaciones.setAfActivos(afActivos);
        afDepreciaciones.setAfUbicaciones(afActivos.getAfUbicaciones());
        afDepreciaciones.setAfCategoria(afActivos.getAfCategorias());
        afDepreciaciones.setAfFechaAdquisicion(UtilsValidacion.fecha(UtilsValidacion.fecha(afDepreciacionesTO.getAfFechaAdquisicion().toString(), "yyyy-MM-dd", "dd/MM/yyyy"), "dd/MM/yyyy"));
        afDepreciaciones.setAfValorAdquision(afDepreciacionesTO.getAfValorAdquisicion());
        afDepreciaciones.setAfValorDepreciacion(afDepreciacionesTO.getAfDepreciacionActual());
        afDepreciaciones.setAfValorResidual(afDepreciacionesTO.getAfValorResidual());
        afDepreciaciones.setAfValorDepreciacionGnd(afDepreciacionesTO.getAfDepreciacionGnd());
        return afDepreciaciones;
    }
}
