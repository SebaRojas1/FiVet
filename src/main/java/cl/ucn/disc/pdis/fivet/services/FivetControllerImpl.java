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

package cl.ucn.disc.pdis.fivet.services;

import cl.ucn.disc.pdis.fivet.model.Persona;
import cl.ucn.disc.pdis.fivet.orm.DAO;
import cl.ucn.disc.pdis.fivet.orm.ORMLiteDAO;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

/**
 * The Fivet controller implementation.
 *
 * @author Sebastian Rojas
 */
@Slf4j
public final class FivetControllerImpl implements FivetController {

    /**
     * The dao of persona objects
     */
    private DAO<Persona> daoPersona;

    public FivetControllerImpl(String dbUrl) throws SQLException {

        ConnectionSource cs = new JdbcConnectionSource(dbUrl);
        this.daoPersona = new ORMLiteDAO<>(cs, Persona.class);

    }

    /**
     * Authentication of a person in the system
     * @param login The login account
     * @param password The password of the user
     * @return a Persona
     */
    @Override
    public Optional<Persona> autenticar(String login, String password) {


        Optional<Persona> persona = this.daoPersona.get("rut", login);

        if (persona.isEmpty()) {
            persona = this.daoPersona.get("email", login);
        }
        if (persona.isEmpty()) {
            return Optional.empty();
        }

        if (BCrypt.checkpw(password, persona.get().getPassword())) {
            return persona;
        }
        return Optional.empty();
    }

    /**
     * Add a Persona in to the backend
     *
     * @param persona  to add
     * @param password to hash
     */
    @Override
    public void add(Persona persona, String password) {
        persona.setPassword(BCrypt.hashpw(password,
                BCrypt.gensalt(12)));
        this.daoPersona.save(persona);
    }
}
