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

import cl.ucn.disc.pdis.fivet.orm.Entity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The model of the persona object
 *
 * @author Sebastiàn Rojas
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DatabaseTable(tableName = "persona")
public final class Persona extends Entity {

    /**
     * The nombre of the person
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String nombre;

    /**
     * The direccion of the person
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String direccion;

    /**
     * The person's mobile phone number
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private Integer telefonoMovil;

    /**
     * The person's landline number
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private Integer telefonoFijo;

    /**
     * The email of the person
     */
    @Getter
    @DatabaseField(canBeNull = false, unique = true)
    private String email;

    /**
     * The rut of the person
     */
    @Getter
    @DatabaseField(canBeNull = false, unique = true)
    private String rut;

}
