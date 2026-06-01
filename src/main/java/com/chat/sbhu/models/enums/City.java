package com.chat.sbhu.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum City {

    BOGOTA("Bogotá"),
    MEDELLIN("Medellín"),
    CALI("Cali"),
    BARRANQUILLA("Barranquilla"),
    CARTAGENA("Cartagena"),
    BUCARAMANGA("Bucaramanga"),
    PEREIRA("Pereira"),
    MANIZALES("Manizales"),
    ARMENIA("Armenia");

    private final String displayName;
}
