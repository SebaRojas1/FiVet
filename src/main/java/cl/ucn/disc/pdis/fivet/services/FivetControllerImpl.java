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

import cl.ucn.disc.pdis.fivet.model.Control;
import cl.ucn.disc.pdis.fivet.model.FichaMedica;
import cl.ucn.disc.pdis.fivet.model.Persona;
import cl.ucn.disc.pdis.fivet.orm.DAO;
import cl.ucn.disc.pdis.fivet.orm.ORMLiteDAO;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The Fivet controller implementation.
 *
 * @author Sebastian Rojas
 */
@Slf4j
public final class FivetControllerImpl implements FivetController {

    /**
     * The dao of the entitys
     */
    private DAO<Persona> daoPersona;
    private DAO<FichaMedica> daoFichaMedica;

    /**
     * The Hasher.
     */
    private final static PasswordEncoder PASSWORD_ENCODER = new Argon2PasswordEncoder();

    public FivetControllerImpl(String dbUrl, boolean b) throws SQLException {

        ConnectionSource cs = new JdbcConnectionSource(dbUrl);
        this.daoPersona = new ORMLiteDAO<>(cs, Persona.class);
        this.daoFichaMedica = new ORMLiteDAO<>(cs, FichaMedica.class);

    }

    /**
     * Check if the Email or Rut exists in the system
     *
     * @param login rut or email
     * @return a persona
     */
    @Override
    public Optional<Persona> retrieveLogin(String login) {

        //TODO hacer el retrieve login y que se utilice dentro del autenticar
        return Optional.empty();
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

        // Verify if the rut is in the database
        if (persona.isEmpty()) {
            persona = this.daoPersona.get("email", login);
        }

        // Verify if the Email is in the database
        if (persona.isEmpty()) {
            return Optional.empty();
        }

        // Check the password to verify that is the correct password
        if (PASSWORD_ENCODER.matches(password, persona.get().getPassword())) {
            return persona;
        }
        // Wrong password, return an empty
        return Optional.empty();
    }

    /**
     * Save a persona into the backend
     * @param persona  to add
     * @param password to hash
     */
    @Override
    public void addPersona(@NonNull Persona persona, @NonNull String password) {
        // Hash password
        persona.setPassword(PASSWORD_ENCODER.encode(password));
        // Save the persona
        this.daoPersona.save(persona);
    }

    /**
     * add a Control in a FichaMedica
     *
     * @param control to add
     */
    @Override
    public void addControl(Control control) {
        Optional<FichaMedica> fichaMedica = this.daoFichaMedica.get(control.getFichaMedica().getNumeroFicha());
        fichaMedica.get().getControles().add(control);
    }

    /**
     * add a FichaMedica in the system
     *
     * @param fichaMedica to add
     */
    @Override
    public void addFichaMedica(FichaMedica fichaMedica) {
        this.daoFichaMedica.save(fichaMedica);
    }

    /**
     * get a FichaMedica by the numeroFicha
     *
     * @param numeroFicha to use
     */
    @Override
    public Optional<FichaMedica> getFichaMedica(Integer numeroFicha) {
        Optional<FichaMedica> fichaMedica = this.daoFichaMedica.get(numeroFicha);
        return fichaMedica;
    }

    /**
     * search 0 or more FichaMedica and return a List of FichaMedica
     *
     * @param q to use
     */
    @Override
    public List<FichaMedica> searchFichaMedica(String q) {
        return null;
    }

    /**
     * Delete a persona by id
     *
     * @param idPersona the id
     */
    @Override
    public void deletePersona(Integer idPersona) {
        //TODO hacer el delete en la implementacion
    }
}
