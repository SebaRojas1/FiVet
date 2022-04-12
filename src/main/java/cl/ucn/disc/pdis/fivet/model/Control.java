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
 *
 */
public class Control extends Entity{

    /**
     *
     */
    private final Date controlDate;

    /**
     *
     */
    private final Float temperature;

    /**
     *
     */
    private final Float weight;

    /**
     *
     */
    private final Float height;

    /**
     *
     */
    private final String diagnosis;

    /**
     *
     */
    private final String veterinariamName;

    /**
     *
     */
    private final Date nextControlDate;

    /**
     *
     * @param controlDate
     * @param temperature
     * @param weight
     * @param height
     * @param diagnosis
     * @param veterinariamName
     * @param nextControlDate
     */
    public Control(Date controlDate, Float temperature, Float weight, Float height, String diagnosis,
                   String veterinariamName, Date nextControlDate){

        super();

        this.controlDate = controlDate;
        this.temperature = temperature;
        this.weight = weight;
        this.height = height;
        this.diagnosis = diagnosis;
        this.veterinariamName = veterinariamName;
        this.nextControlDate = nextControlDate;

    }

    public Date getControlDate() {
        return this.controlDate;
    }

    public Float getTemperature() {
        return this.temperature;
    }

    public Float getWeight() {
        return this.weight;
    }

    public Float getHeight() {
        return this.height;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public String getVeterinariamName() {
        return this.veterinariamName;
    }

    public Date getNextControlDate() {
        return this.nextControlDate;
    }


}
