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

import cl.ucn.disc.pdis.fivet.orm.DAO;
import cl.ucn.disc.pdis.fivet.orm.ORMLiteDAO;
import cl.ucn.disc.pdis.fivet.orm.ZonedDateTimeType;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Cleanup;
import lombok.SneakyThrows;
import cl.ucn.disc.pdis.fivet.model.Persona;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Testing the Fivet Controller
 *
 * @author Sebastian Rojas
 */
@Slf4j
public class TestFivetController {

    /**
     * The Main DAO
     */
    @SneakyThrows
    @Test
    @DisplayName("The Main Test")
    void theMain() {
        log.debug("Starting the MainTest ..");

        log.debug("Registering the ZonedDateTimeType ..");
        DataPersisterManager.registerDataPersisters(ZonedDateTimeType.INSTANCE);

        // The password encoder
        PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

        // The driver connection
        String databaseUrl = "jdbc:h2:mem:fivet";
        // String databaseUrl = "jdbc:postgresql:fivet";
        // String databaseUrl = "jdbc:sqlite:fivet.db";
        // String databaseUrl = "jdbc:mysql://localhost:3306/fivet";

        log.debug("Building the Connection, using: {}, databaseUrl");
        // Build the Connection with auto clase (clean up)
        @Cleanup
        ConnectionSource cs = new JdbcConnectionSource(databaseUrl);

        log.debug("Dropping the tables..");
        // Drop the database
        TableUtils.dropTable(cs, Persona.class, true);

        log.debug("Creating the tables ..");
        // Create the database
        TableUtils.createTable(cs, Persona.class);

        log.debug("Building the ORMLiteDAO ..");
        // The dao
        DAO<Persona> dao = new ORMLiteDAO<>(cs, Persona.class);

        // Instanciating the controller
        FivetController controller = new FivetControllerImpl(databaseUrl, true);

        // Testing add method
        log.debug("Creating the persona");
        {
            Persona persona = Persona.builder()
                    .nombre("Seba")
                    .rut("123456766")
                    .email("hola12@gmail.com")
                    .direccion("si 1234")
                    .password(passwordEncoder.encode("seba123"))
                    .build();

            // Adding the person with the password hashed
            controller.add(persona, persona.getPassword());
        }

        // Testing autenticar method
        {
            // Normal login with the rut
            controller.autenticar("123456766", "123");
            // Normal login with the email
            controller.autenticar("hola12@gmail.com", "123");
            // Login with a invalid password
            controller.autenticar("hola12@gmail.com", "12344");
            // Login with a invalid rut or email
            controller.autenticar("hola", "123");
        }



    }
}
