package com.superfinanciera.mycrud.utils;

public class Constant {
    private Constant() {
    }
    /**
     *
     * Path general
     */
    public static final String PATH = "";
    public static final Long ID_CUENTA_AHORRO = 56L;
    public static final String NOMBRE_CUENTA_AHORRO = "cuenta de ahorros";

    public static final Long ID_CUENTA_CORRIENTE = 33L;
    public static final String NOMBRE_CUENTA_CORRIENTE = "cuenta corriente";

    public class EstadoCuenta {
        public static final Long ID_ACTIVA= 1L;
        public static final String ESTADO_CUENTA_ACTIVA = "Activa";
        public static final Long ID_INACTIVA = 2L;
        public static final String ESTADO_CUENTA_INACTIVA = "Inactiva";
        public static final Long ID_CANCELADA = 3L;
        public static final String ESTADO_CUENTA_CANCELADA = "Cancelada";

    }
}
