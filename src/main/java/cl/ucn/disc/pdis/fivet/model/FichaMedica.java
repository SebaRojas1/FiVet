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
public final class FichaMedica extends Entity{

    /**
     *
     */
    private final Integer indexCardNumber;

    /**
     *
     */
    private final String patientName;

    /**
     *
     */
    private final String species;

    /**
     *
     */
    private final Date birthday;

    /**
     *
     */
    private final String race;

    /**
     *
     */
    private final Character sex;

    /**
     *
     */
    private final String color;

    /**
     *
     */
    private final String type;

    /**
     *
     * @param indexCardNumber
     * @param patientName
     * @param species
     * @param birthday
     * @param race
     * @param sex
     * @param color
     * @param type
     */
    public FichaMedica(Integer indexCardNumber, String patientName, String species, Date birthday, String race,
                       Character sex, String color, String type) {

        this.indexCardNumber = indexCardNumber;
        this.patientName = patientName;
        this.species = species;
        this.birthday = birthday;
        this.race = race;
        this.sex = sex;
        this.color = color;
        this.type = type;

    }

    public Integer getIndexCardNumber() {
        return this.indexCardNumber;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public String getSpecies() {
        return this.species;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public String getRace() {
        return this.race;
    }

    public Character getSex() {
        return this.sex;
    }

    public String getColor() {
        return this.color;
    }

    public String getType() {
        return this.type;
    }

}
