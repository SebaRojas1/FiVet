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
import cl.ucn.disc.pdis.fivet.model.Examen;
import cl.ucn.disc.pdis.fivet.model.FichaMedica;
import cl.ucn.disc.pdis.fivet.model.Persona;
import cl.ucn.disc.pdis.fivet.orm.DAO;
import cl.ucn.disc.pdis.fivet.orm.ORMLiteDAO;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The Fivet controller implementation.
 *
 * @author Sebastian Rojas
 */
@Slf4j
public final class FivetControllerImpl implements FivetController {

    /*
     * The dao of the entitys
     */
    private final DAO<Persona> daoPersona;
    private final DAO<FichaMedica> daoFichaMedica;

    /*
     * The Hasher.
     */
    private final PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    /**
     * FivetController constructor.
     *
     * @param dbUrl Database url
     * @param b bool
     * @throws SQLException to throw
     */
    public FivetControllerImpl(String dbUrl, boolean b) throws SQLException {

        ConnectionSource cs = new JdbcConnectionSource(dbUrl);
        this.daoPersona = new ORMLiteDAO<>(cs, Persona.class);
        this.daoFichaMedica = new ORMLiteDAO<>(cs, FichaMedica.class);

        // Create the table from Persona annotations
        TableUtils.createTableIfNotExists(cs, Persona.class);

        // Create the table from Persona annotations
        TableUtils.createTableIfNotExists(cs, FichaMedica.class);

        // Create the table from Persona annotations
        TableUtils.createTableIfNotExists(cs, Control.class);

        // Create the table from Persona annotations
        TableUtils.createTableIfNotExists(cs, Examen.class);


    }

    /**
     * Check if the Email or Rut exists in the system.
     *
     * @param login rut or email
     * @return a persona
     */
    @Override
    public Optional<Persona> retrieveByLogin(String login) {
        Optional<Persona> persona = this.daoPersona.get("rut", login);
        // Check if the Persona is already founded with the rut
        if (persona.isEmpty()) {
            persona = this.daoPersona.get("email", login);
        }
        // Check if the Persona is already founded with the email
        if (persona.isEmpty()) {
            return Optional.empty();
        }
        return persona;
    }

    /**
     * Authentication of a person in the system.
     *
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
        if (passwordEncoder.matches(password, persona.get().getPassword())) {
            return persona;
        }
        // Wrong password, return an empty
        return Optional.empty();
    }

    /**
     * Save a persona into the backend.
     *
     * @param persona  to add
     * @param password to hash
     */
    @Override
    public void addPersona(@NonNull Persona persona, @NonNull String password) {
        // Hash password
        persona.setPassword(passwordEncoder.encode(password));
        // Save the persona
        this.daoPersona.save(persona);
    }

    /**
     * add a Control in a FichaMedica.
     *
     * @param control to add
     */
    @Override
    public void addControl(@NonNull Control control) {
        Optional<FichaMedica> fichaMedica = this.daoFichaMedica.get(control.getFichaMedica().getNumeroFicha());
        Optional<Persona> veterinario = this.daoPersona.get("rut", control.getVeterinario().getRut());
        // Check if the FichaMedica and the Veterinario is present in the system
        if (fichaMedica.isPresent() && veterinario.isPresent()) {
            control.setVeterinario(veterinario.get());
            control.setFichaMedica(fichaMedica.get());
        }
        fichaMedica.get().getControles().add(control);
    }

    /** add a FichaMedica in the system.
     *
     * @param fichaMedica to add
     */
    @Override
    public void addFichaMedica(@NonNull FichaMedica fichaMedica) {
        Collection<Persona> personas = daoPersona.getAll();
        for (Persona persona : personas) {
            // Check if the duenio exists in the system
            if (persona.getRut().equals(fichaMedica.getDuenio().getRut())) {
                fichaMedica.setDuenio(persona);
                break;
            }
        }
        this.daoFichaMedica.save(fichaMedica);
    }

    /**
     * get a FichaMedica by the numeroFicha.
     *
     * @param numeroFicha to use
     */
    @Override
    public Optional<FichaMedica> getFichaMedica(Integer numeroFicha) {
        Optional<FichaMedica> fichaMedica = this.daoFichaMedica.get(numeroFicha);
        return fichaMedica;
    }

    /**
     * if the strings matches return true, false in other case.
     *
     * @param q to use
     * @param atributo to use
     * @return a boolean
     */
    @Override
    public boolean stringMatch(String q, String atributo) {
        int coincidencias = StringUtils.countMatches(q, atributo);
        if (coincidencias > 0) {
            return true;
        }
        return false;
    }

    /**
     * return a filtered list of FichaMedica.
     *
     * @param fichaMedicaEncontradas List of FichaMedica
     * @return list of FichaMedica
     */
    @Override
    public Collection<FichaMedica> filterRepeatedFichaMedica(Collection<FichaMedica> fichaMedicaEncontradas) {
        Collection<FichaMedica> fichaMedicaNoRepeditas = new ArrayList<>();
        for (FichaMedica fichaMedicaEncontrada : fichaMedicaEncontradas) {
            boolean repetida = false;
            // search repeated FichaMedica
            for (FichaMedica fichaMedica : fichaMedicaNoRepeditas) {
                if (fichaMedicaEncontrada.getNumeroFicha() == fichaMedica.getNumeroFicha()) {
                    repetida = true;
                    break;
                }
            }
            // add no repeated FichaMedica
            if (repetida == false) {
                fichaMedicaNoRepeditas.add(fichaMedicaEncontrada);
            }
        }
        return fichaMedicaNoRepeditas;
    }

    /**
     * Return all FichaMedica in the system.
     *
     * @return Collection of FichaMedica
     */
    @Override
    public List<FichaMedica> getAllFichaMedica() {
        List<FichaMedica> lista = this.daoFichaMedica.getAll();
        return lista;
    }
}
