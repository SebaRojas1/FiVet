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

package cl.ucn.disc.pdis.fivet;

import cl.ucn.disc.pdis.fivet.model.Persona;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 * Testing class for ORMLite and database
 *
 * @author Sebastiàn Rojas
 */
@Slf4j
class testDatabase {

    @Test
    public void testDatabase() throws SQLException {

        String databaseUrl = "jdbc:h2:mem:fivet_db";

        try(ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl)) {

            TableUtils.createTableIfNotExists(connectionSource, Persona.class);

            Dao<Persona, Long> daoPersona = DaoManager.createDao(connectionSource, Persona.class);

            Persona persona = new Persona();

            //TODO: arreglar la instancia de la clase persona y los assertions
            Persona persona = new Persona("Sebastian", "Rojas", "arturo prat 111", 1111111,
                    22222222, "test@gmail.com", "20.218.430-8");

            int tuples = daoPersona.create(persona);
            //log.debug("Id: {}", persona.getId());

            Assertions.assertEquals(1, tuples, "Save tuples != 1");

            Persona personaDb = daoPersona.queryForId(persona.getId());

            Assertions.assertEquals(persona.getNombre(), personaDb.getNombre(), "Nombre not equals!");
            Assertions.assertEquals(persona.getApellido(), personaDb.getApellido(), "Apellido not equals!");
            Assertions.assertEquals(persona.getRut(), personaDb.getRut(), "Rut not equals!");

        } catch (Exception e) {
            //log.debug("The error {}", e);
        }
    }

}
