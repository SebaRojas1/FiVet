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
 * The model of the control object created for the patients
 *
 * @author Sebastiàn Rojas
 */
public class Control extends Entity {

    /**
     * The fecha of the control carried out
     */
    private final Date fecha;

    /**
     * The patient's temperatura in the control
     */
    private final Float temperatura;

    /**
     * The peso of the patient in the control
     */
    private final Float peso;

    /**
     * The altura of the patient in the control
     */
    private final Float altura;

    /**
     * The patient's diagnostico
     */
    private final String diagnostico;

    /**
     * The vet's name
     */
    private final String nomVeterinario;

    /**
     * The date of the next control
     */
    private final Date siguienteFecha;

    /**
     * The constructor of control
     * @param fecha The control date
     * @param temperatura The temperatura
     * @param peso The peso
     * @param altura The altura
     * @param diagnostico The diagnostico
     * @param nomVeterinario The name of veterinarian
     * @param siguienteFecha The next control date
     */

    /**
     * Empty constructor required.
     */
    Control() {

    }

    public Control(Date fecha, Float temperatura, Float peso, Float altura, String diagnostico,
                   String nomVeterinario, Date siguienteFecha) {

        super();

        this.fecha = fecha;
        this.temperatura = temperatura;
        this.peso = peso;
        this.altura = altura;
        this.diagnostico = diagnostico;
        this.nomVeterinario = nomVeterinario;
        this.siguienteFecha = siguienteFecha;

    }

    public Date getFecha() {
        return this.fecha;
    }

    public Float getTemperatura() {
        return this.temperatura;
    }

    public Float getPeso() {
        return this.peso;
    }

    public Float getAltura() {
        return this.altura;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public String getNomVeterinario() {
        return this.nomVeterinario;
    }

    public Date getSiguienteFecha() {
        return this.siguienteFecha;
    }


}
