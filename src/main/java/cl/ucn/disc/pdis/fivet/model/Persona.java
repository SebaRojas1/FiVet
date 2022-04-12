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
 * The model of the persona object.
 */
public final class Persona extends Entity{

    /**
     * The name of the person
     */
    private final String name;

    /**
     * The address of the person
     */
    private final String address;

    /**
     * The person's mobile phone number
     */
    private final Integer mobilePhoneNumber;

    /**
     * The person's landline number
     */
    private final Integer landlinePhoneNumber;

    /**
     * The email of the person
     */
    private final String email;

    /**
     * The rut of the person
     */
    private final String rut;

    /**
     * The constructor of the Persona
     * @param name The name
     * @param address The address
     * @param mobilePhoneNumber The mobile number
     * @param landlinePhoneNumber The landline number
     * @param email The email
     * @param rut The rut
     */
    public Persona(String name, String address, Integer mobilePhoneNumber, Integer landlinePhoneNumber,
                   String email, String rut) {

        this.name = name;
        this.address = address;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.landlinePhoneNumber = landlinePhoneNumber;
        this.email = email;
        this.rut = rut;

    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public Integer getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }

    public Integer getLandlinePhoneNumber() {
        return this.landlinePhoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRut() {
        return this.rut;
    }
}
