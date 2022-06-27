/*
 * Copyright (c) 2022 Sebastian Rojas Rodriguez sebastian.rojas04@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.pdis.fivet.services;

import cl.ucn.disc.pdis.fivet.grpc.ControlEntity;
import cl.ucn.disc.pdis.fivet.grpc.FichaMedicaEntity;
import cl.ucn.disc.pdis.fivet.grpc.PersonaEntity;
import cl.ucn.disc.pdis.fivet.model.Control;
import cl.ucn.disc.pdis.fivet.model.FichaMedica;
import cl.ucn.disc.pdis.fivet.model.Persona;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The model adapter
 *
 * @author Sebastian Rojas
 */
@UtilityClass
public final class ModelAdapter {

    /**
     * Persona java to Persona gRPC
     * @param persona Persona java
     * @return a personaEntity
     */
    public static PersonaEntity build(final Persona persona) {
        return null;
    }

    /**
     * Persona gRPC to Persona java
     * @param persona PersonaEntity
     * @return a persona java
     */
    public static Persona build(final PersonaEntity persona) {
        return null;
    }

    /**
     * FichaMedica java to FichaMedica gRPC
     * @param fichaMedica FichaMedica java
     * @return a FichaMedicaEntity
     */
    public static FichaMedicaEntity build(final FichaMedica fichaMedica) {
        return null;
    }

    /**
     * FichaMedica gRPC to FichaMedica java
     * @param fichaMedica FichaMedicaEntity
     * @return a FichaMedica java
     */
    public static FichaMedica build(final FichaMedicaEntity fichaMedica) {
        return null;
    }

    /**
     * Control java to Control gRPC
     * @param control Control java
     * @return ControlEntity
     */
    public static ControlEntity build(final Control control) {
        return null;
    }

    /**
     * Control gRPC to Control java
     * @param control ControlEntity
     * @return a Control java
     */
    public static Control build(final ControlEntity control) {
        return null;
    }

    /**
     * String to ZonedDateTime
     * @param dateTime the date
     * @return a ZonedDateTime
     */
    public static ZonedDateTime build(final String dateTime) {
        return ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }


}
