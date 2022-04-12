/*
 * Copyright (c) 2022 Sebastián Rojas Rodriguez sebastian.rojas04@alumnos.ucn.cl
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

package cl.ucn.disc.pdis.fivet.model;

/**
 * The model of the persona object
 *
 * @author Sebastiàn Rojas
 */
public final class Persona extends Entity {

    /**
     * The nombre of the person
     */
    private String nombre;

    /**
     * The direccion of the person
     */
    private String direccion;

    /**
     * The person's mobile phone number
     */
    private Integer telefonoMovil;

    /**
     * The person's landline number
     */
    private Integer telefonoFijo;

    /**
     * The email of the person
     */
    private String email;

    /**
     * The rut of the person
     */
    private String rut;

    /**
     * Empty constructor required.
     */
    Persona() {

    }

    /**
     * The constructor of the Persona
     * @param nombre The name
     * @param direccion The address
     * @param telefonoMovil The mobile number
     * @param telefonoFijo The landline number
     * @param email The email
     * @param rut The rut
     */
    public Persona(String nombre, String direccion, Integer telefonoMovil, Integer telefonoFijo,
                   String email, String rut) {

        this.nombre = nombre;
        this.direccion = direccion;
        this.telefonoMovil = telefonoMovil;
        this.telefonoFijo = telefonoFijo;
        this.email = email;
        this.rut = rut;

    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Integer getTelefonoMovil() {
        return this.telefonoMovil;
    }

    public Integer getTelefonoFijo() {
        return this.telefonoFijo;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRut() {
        return this.rut;
    }
}
