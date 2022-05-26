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

import cl.ucn.disc.pdis.fivet.orm.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

/**
 * The model of the FichaMedica object
 *
 * @author Sebastian Rojas
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DatabaseTable
public final class FichaMedica extends BaseEntity {

    /**
     * The number of the record card
     */
    @Getter
    @DatabaseField(canBeNull = false, unique = true)
    private Integer numeroFicha;

    /**
     * The patient's name
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String nombrePaciente;

    /**
     * The especie of the patient
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String especie;

    /**
     * The date of birth of the patient
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private LocalDate fechaNacimiento;

    /**
     * The raza of the patient
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String raza;

    /**
     * The sexo of the patient
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private Sexo sexo;

    /**
     * The color of the patient
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String color;

    /**
     * The tipo of animal
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private String tipo;

    /**
     * The duenio
     */
    @Getter
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "persona_id")
    private Persona duenio;

    /**
     * The controles
     */
    @Getter
    @ForeignCollectionField(eager = true, orderColumnName = "fecha")
    private Collection<Control> controles;

    /**
     * Append a control
     */
    public void add(Control control) {
        this.controles.add(control);
    }

    /**
     * The sexo
     */
    public enum Sexo {
        MACHO,
        HEMBRA
    }
}
