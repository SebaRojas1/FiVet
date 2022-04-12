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

import java.util.Date;

/**
 * The model of the FichaMedica object
 *
 * @author Sebastiàn Rojas
 */
public final class FichaMedica extends Entity {

    /**
     * The number of the record card
     */
    private Integer numeroFicha;

    /**
     * The patient's name
     */
    private String nombrePaciente;

    /**
     * The especie of the patient
     */
    private String especie;

    /**
     * The date of birth of the patient
     */
    private Date fechaNacimiento;

    /**
     * The raza of the patient
     */
    private String raza;

    /**
     * The sexo of the patient
     */
    private Character sexo;

    /**
     * The color of the patient
     */
    private String color;

    /**
     * The tipo of animal
     */
    private String tipo;

    /**
     * Empty constructor required.
     */
    FichaMedica() {

    }

    /**
     * The constructor of the FichaMedica
     * @param numeroFicha The record card number
     * @param nombrePaciente The patient's name
     * @param especie The species
     * @param fechaNacimiento The birthday
     * @param raza The race
     * @param sexo The sex
     * @param color The color
     * @param tipo The type of animal
     */
    public FichaMedica(Integer numeroFicha, String nombrePaciente, String especie, Date fechaNacimiento, String raza,
                       Character sexo, String color, String tipo) {

        this.numeroFicha = numeroFicha;
        this.nombrePaciente = nombrePaciente;
        this.especie = especie;
        this.fechaNacimiento = fechaNacimiento;
        this.raza = raza;
        this.sexo = sexo;
        this.color = color;
        this.tipo = tipo;

    }

    public Integer getNumeroFicha() {
        return this.numeroFicha;
    }

    public String getNombrePaciente() {
        return this.nombrePaciente;
    }

    public String getEspecie() {
        return this.especie;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public String getRaza() {
        return this.raza;
    }

    public Character getSexo() {
        return this.sexo;
    }

    public String getColor() {
        return this.color;
    }

    public String getTipo() {
        return this.tipo;
    }

}
