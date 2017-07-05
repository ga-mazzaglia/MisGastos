package com.cuentasclaras.statics

/**
 * Created by gmazzaglia on 4/7/17.
 */
enum MovementsType {
    GASTOS_PERSONALES(1),
    GASTOS_COMPARTIDOS(2),
    DINERO_ENTREGADO(3),
    DINERO_RECIBIDO(4)

    Integer index

    MovementsType(Integer index) {
        this.index = index;
    }
}