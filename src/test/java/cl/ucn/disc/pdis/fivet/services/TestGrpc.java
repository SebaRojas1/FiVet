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


import cl.ucn.disc.pdis.fivet.grpc.*;
import cl.ucn.disc.pdis.fivet.orm.LocalDateType;
import cl.ucn.disc.pdis.fivet.orm.ZonedDateTimeType;
import com.asarkar.grpc.test.GrpcCleanupExtension;
import com.asarkar.grpc.test.Resources;
import com.j256.ormlite.field.DataPersisterManager;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Iterator;

/**
 * Test gRPC
 */
@Slf4j
@ExtendWith(GrpcCleanupExtension.class)
public class TestGrpc {

    /**
     * Test the RPC
     * @param resources resources to use
     */
    @Test
    public void testGrpc(Resources resources) throws IOException, SQLException {
        log.debug("Starting TestGrpc... ");

        // Unique server name
        String serverName = InProcessServerBuilder.generateName();
        log.debug("Testing serverName <{}> ... ", serverName);

        // Registering the LocalDateType
        DataPersisterManager.registerDataPersisters(LocalDateType.INSTANCE);

        // Registering the ZonedDateTimeType
        DataPersisterManager.registerDataPersisters(ZonedDateTimeType.INSTANCE);

        // Initialize the server
        Server server = InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(new FivetServiceImpl("jdbc:h2:mem:fivet")).build().start();
        resources.register(server, Duration.ofSeconds(10));

        // Initialize the channel
        ManagedChannel channel = InProcessChannelBuilder.forName(serverName).directExecutor().build();
        resources.register(channel, Duration.ofSeconds(10));

        // The Stub testing
        FivetServiceGrpc.FivetServiceBlockingStub stub = FivetServiceGrpc.newBlockingStub(channel);

        // AddPersona
        log.debug("Testing AddPersona ..");
        {
            // No Rut
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                PersonaReply addPersonaTest = stub.addPersona(AddPersonaReq.newBuilder()
                        .setPersona(PersonaEntity.newBuilder()
                                .setNombre("Sebastian")
                                .setDireccion("micasa123")
                                .setEmail("seba@gmail.com")
                                .build()).build());
            });

            // No Nombre
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                PersonaReply addPersonaTest = stub.addPersona(AddPersonaReq.newBuilder()
                        .setPersona(PersonaEntity.newBuilder()
                                .setRut("20218430-8")
                                .setDireccion("micasa123")
                                .setEmail("seba@gmail.com")
                                .build()).build());
            });

            // No Email
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                        PersonaReply addPersonaTest = stub.addPersona(AddPersonaReq.newBuilder()
                                .setPersona(PersonaEntity.newBuilder()
                                        .setRut("20218430-8")
                                        .setNombre("Sebastian")
                                        .setDireccion("micasa123")
                                        .build()).build());
            });

            // Correct add persona
            PersonaReply addPersona = stub.addPersona(AddPersonaReq.newBuilder()
                    .setPersona(PersonaEntity.newBuilder()
                            .setRut("20218430-8")
                            .setNombre("Sebastian")
                            .setDireccion("micasa123")
                            .setEmail("seba@gmail.com")
                            .build()).build());
            log.debug("PersonaReply {}", addPersona);
            Assertions.assertEquals("20218430-8", addPersona.getPersona().getRut(), "Wrong Rut");

            // Rut Unique
            Assertions.assertThrowsExactly(StatusRuntimeException.class, () -> {
                PersonaReply addPersonaTest = stub.addPersona(AddPersonaReq.newBuilder()
                        .setPersona(PersonaEntity.newBuilder()
                                .setRut("20218430-8")
                                .setNombre("Sebastian")
                                .setDireccion("micasa123")
                                .setEmail("otrapersona@gmail.com")
                                .build()).build());
            });

            // Email Unique
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                PersonaReply addPersonaTest = stub.addPersona(AddPersonaReq.newBuilder()
                        .setPersona(PersonaEntity.newBuilder()
                                .setRut("19473954-0")
                                .setNombre("Sebastian")
                                .setDireccion("micasa123")
                                .setEmail("seba@gmail.com")
                                .build()).build());
            });

            // Adding veterinario
            PersonaReply veterinario = stub.addPersona(AddPersonaReq.newBuilder()
                    .setPersona(PersonaEntity.newBuilder()
                            .setRut("19248292-4")
                            .setNombre("Alexis")
                            .setDireccion("dondevivo999")
                            .setEmail("veterinario@gmail.com")
                            .build()).build());
        }

        // AddFichaMedica
        log.debug("Testing AddFichaMedica");
        {
            // Duenio
            PersonaEntity duenio = PersonaEntity.newBuilder()
                    .setRut("20218430-8")
                    .setNombre("Sebastian")
                    .setDireccion("micasa123")
                    .setEmail("seba@gmail.com")
                    .build();

            // No numeroFicha
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setColor("azul")
                                .setDuenio(duenio)
                                .setEspecie("perro")
                                .setFechaNacimiento("2000-05-11")
                                .setNombrePaciente("perrowuau")
                                .setRaza("labrador")
                                .setSexo(SexoEntity.MACHO)
                                .setTipo("comun")
                                .build()).build());
            });

            // No nombrePaciente
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setColor("azul")
                                .setDuenio(duenio)
                                .setEspecie("perro")
                                .setFechaNacimiento("2000-05-11")
                                .setNumeroFicha(1)
                                .setRaza("labrador")
                                .setSexo(SexoEntity.MACHO)
                                .setTipo("comun")
                                .build()).build());
            });

            // No especie
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setColor("azul")
                                .setDuenio(duenio)
                                .setFechaNacimiento("2000-05-11")
                                .setNombrePaciente("perrowuau")
                                .setNumeroFicha(1)
                                .setRaza("labrador")
                                .setSexo(SexoEntity.MACHO)
                                .setTipo("comun")
                                .build()).build());
            });

            // No fechaNacimiento
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setColor("azul")
                                .setDuenio(duenio)
                                .setEspecie("perro")
                                .setNombrePaciente("perrowuau")
                                .setNumeroFicha(1)
                                .setRaza("labrador")
                                .setSexo(SexoEntity.MACHO)
                                .setTipo("comun")
                                .build()).build());
            });

            // No raza
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setColor("azul")
                                .setDuenio(duenio)
                                .setEspecie("perro")
                                .setFechaNacimiento("2000-05-11")
                                .setNombrePaciente("perrowuau")
                                .setNumeroFicha(1)
                                .setSexo(SexoEntity.MACHO)
                                .setTipo("comun")
                                .build()).build());
            });

            // No sexo
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setColor("azul")
                                .setDuenio(duenio)
                                .setEspecie("perro")
                                .setFechaNacimiento("2000-05-11")
                                .setNombrePaciente("perrowuau")
                                .setNumeroFicha(1)
                                .setRaza("labrador")
                                .setTipo("comun")
                                .build()).build());
            });

            // No color
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setDuenio(duenio)
                                .setEspecie("perro")
                                .setFechaNacimiento("2000-05-11")
                                .setNombrePaciente("perrowuau")
                                .setNumeroFicha(1)
                                .setRaza("labrador")
                                .setSexo(SexoEntity.MACHO)
                                .setTipo("comun")
                                .build()).build());
            });

            // No tipo
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setColor("azul")
                                .setDuenio(duenio)
                                .setEspecie("perro")
                                .setFechaNacimiento("2000-05-11")
                                .setNombrePaciente("perrowuau")
                                .setNumeroFicha(1)
                                .setRaza("labrador")
                                .setSexo(SexoEntity.MACHO)
                                .build()).build());
            });

            // Correct add FichaMedica
            FichaMedicaReply fichaMedicaReply = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                    .setFichaMedica(FichaMedicaEntity.newBuilder()
                    .setColor("azul")
                    .setDuenio(duenio)
                    .setEspecie("perro")
                    .setFechaNacimiento("2000-05-11")
                    .setNombrePaciente("perrowuau")
                    .setNumeroFicha(1)
                    .setRaza("labrador")
                    .setSexo(SexoEntity.MACHO)
                    .setTipo("comun")
                    .build()).build());
            log.debug("FichaMedicaReply {}", fichaMedicaReply);
            Assertions.assertEquals(1, fichaMedicaReply.getFichaMedica().getNumeroFicha()
                    , "Wrong numeroFicha");

            // numeroFicha Unique
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReplyTest = stub.addFichaMedica(AddFichaMedicaReq.newBuilder()
                        .setFichaMedica(FichaMedicaEntity.newBuilder()
                                .setColor("azul")
                                .setDuenio(duenio)
                                .setEspecie("perro")
                                .setFechaNacimiento("2000-05-11")
                                .setNombrePaciente("perrowuau")
                                .setNumeroFicha(1)
                                .setRaza("labrador")
                                .setSexo(SexoEntity.MACHO)
                                .setTipo("comun")
                                .build()).build());
            });
        }

        // addControl
        log.debug("Testing AddControl ..");
        {
            // Duenio
            PersonaEntity duenio = PersonaEntity.newBuilder()
                    .setRut("20218430-8")
                    .setNombre("Sebastian")
                    .setDireccion("micasa123")
                    .setEmail("seba@gmail.com")
                    .build();

            // Veterinario
            PersonaEntity veterinario = PersonaEntity.newBuilder()
                    .setRut("19248292-4")
                    .setNombre("Alexis")
                    .setDireccion("dondevivo999")
                    .setEmail("veterinario@gmail.com")
                    .build();

            // FichaMedica
            FichaMedicaEntity fichaMedica = FichaMedicaEntity.newBuilder()
                    .setColor("azul")
                    .setDuenio(duenio)
                    .setEspecie("perro")
                    .setFechaNacimiento("2000-05-11")
                    .setNombrePaciente("perrowuau")
                    .setNumeroFicha(1)
                    .setRaza("labrador")
                    .setSexo(SexoEntity.MACHO)
                    .setTipo("comun")
                    .build();

            // No fecha
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addControl(AddControlReq.newBuilder()
                        .setControl(ControlEntity.newBuilder()
                                .setAltura(1)
                                .setVeterinario(veterinario)
                                .setDiagnostico("bien de salud")
                                .setPeso(2)
                                .setTemperatura(3)
                                .setFichaMedica(fichaMedica)
                                .build())
                        .build());
            });

            // No fichaMedica
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addControl(AddControlReq.newBuilder()
                        .setControl(ControlEntity.newBuilder()
                                .setFecha("2016-10-05T08:20:10+05:30[Asia/Kolkata]")
                                .setAltura(1)
                                .setVeterinario(veterinario)
                                .setDiagnostico("bien de salud")
                                .setPeso(2)
                                .setTemperatura(3)
                                .build())
                        .build());
            });

            // No temperatura
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addControl(AddControlReq.newBuilder()
                        .setControl(ControlEntity.newBuilder()
                                .setFecha("2016-10-05T08:20:10+05:30[Asia/Kolkata]")
                                .setAltura(1)
                                .setVeterinario(veterinario)
                                .setDiagnostico("bien de salud")
                                .setPeso(2)
                                .setFichaMedica(fichaMedica)
                                .build())
                        .build());
            });

            // No peso
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addControl(AddControlReq.newBuilder()
                        .setControl(ControlEntity.newBuilder()
                                .setFecha("2016-10-05T08:20:10+05:30[Asia/Kolkata]")
                                .setAltura(1)
                                .setVeterinario(veterinario)
                                .setDiagnostico("bien de salud")
                                .setTemperatura(3)
                                .setFichaMedica(fichaMedica)
                                .build())
                        .build());
            });

            // No altura
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addControl(AddControlReq.newBuilder()
                        .setControl(ControlEntity.newBuilder()
                                .setFecha("2016-10-05T08:20:10+05:30[Asia/Kolkata]")
                                .setVeterinario(veterinario)
                                .setDiagnostico("bien de salud")
                                .setPeso(2)
                                .setTemperatura(3)
                                .setFichaMedica(fichaMedica)
                                .build())
                        .build());
            });

            // No diagnostico
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addControl(AddControlReq.newBuilder()
                        .setControl(ControlEntity.newBuilder()
                                .setFecha("2016-10-05T08:20:10+05:30[Asia/Kolkata]")
                                .setAltura(1)
                                .setVeterinario(veterinario)
                                .setPeso(2)
                                .setTemperatura(3)
                                .setFichaMedica(fichaMedica)
                                .build())
                        .build());
            });

            // No veterinario
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.addControl(AddControlReq.newBuilder()
                        .setControl(ControlEntity.newBuilder()
                                .setFecha("2016-10-05T08:20:10+05:30[Asia/Kolkata]")
                                .setAltura(1)
                                .setDiagnostico("bien de salud")
                                .setPeso(2)
                                .setTemperatura(3)
                                .setFichaMedica(fichaMedica)
                                .build())
                        .build());
            });

            // Correct add control
            FichaMedicaReply fichaMedicaReply = stub.addControl(AddControlReq.newBuilder()
                        .setControl(ControlEntity.newBuilder()
                                .setFecha("2016-10-05T08:20:10+05:30[Asia/Kolkata]")
                                .setAltura(1)
                                .setVeterinario(veterinario)
                                .setDiagnostico("bien de salud")
                                .setPeso(2)
                                .setTemperatura(3)
                                .setFichaMedica(fichaMedica)
                                .build())
                        .build());
            log.debug("FichaMedicaReply {}", fichaMedicaReply);
            Assertions.assertEquals(1, fichaMedicaReply.getFichaMedica().getNumeroFicha()
                    , "Wrong numeroFicha");
        }

        // Authenticate
        log.debug("Testing Authenticate ..");
        {
            // No password
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                PersonaReply personaReply = stub.autenticate(AutenticateReq.newBuilder()
                        .setLogin("seba@gmail.com")
                        .build());
            });

            // No login
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                PersonaReply personaReply = stub.autenticate(AutenticateReq.newBuilder()
                        .setPassword("a")
                        .build());
            });

            // Wrong password
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                PersonaReply personaReply = stub.autenticate(AutenticateReq.newBuilder()
                        .setLogin("seba@gmail.com")
                        .setPassword("xxxxxxxxxxxx")
                        .build());
            });

            // Wrong login
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                PersonaReply personaReply = stub.autenticate(AutenticateReq.newBuilder()
                        .setLogin("seba")
                        .setPassword("a")
                        .build());
            });

            // Correct login
            PersonaReply personaReply = stub.autenticate(AutenticateReq.newBuilder()
                    .setLogin("seba@gmail.com")
                    .setPassword("a")
                    .build());
            log.debug("PersonaReply: {}", personaReply);
            Assertions.assertEquals("20218430-8", personaReply.getPersona().getRut(), "Wrong Rut");
        }

        // RetrieveFichaMedica
        log.debug("Testing RetrieveFichaMedica ..");
        {
            // Wrong number
            Assertions.assertThrows(StatusRuntimeException.class, () -> {
                FichaMedicaReply fichaMedicaReply = stub.retrieveFichaMedica(RetrieveFichaMedicaReq.newBuilder()
                        .setNumeroFicha(1000)
                        .build());
            });

            // Correct retrieve
            FichaMedicaReply fichaMedicaReply = stub.retrieveFichaMedica(RetrieveFichaMedicaReq.newBuilder()
                    .setNumeroFicha(1)
                    .build());
            log.debug("PersonaReply: {}", fichaMedicaReply);
            Assertions.assertEquals(1, fichaMedicaReply.getFichaMedica().getNumeroFicha()
                    , "Wrong FichaMedica");
        }

        // SearchFichaMedica
        log.debug("Testing SearchFichaMedica ..");
        {
            // Numeric query
            Iterator<FichaMedicaReply> fichaMedicaReplyList1 = stub.searchFichaMedica(SearchFichaMedicaReq.newBuilder()
                    .setQuery("1")
                    .build());
            FichaMedicaReply fichaMedicaReply1 = fichaMedicaReplyList1.next();
            log.debug("First FichaMedica: {}", fichaMedicaReply1.getFichaMedica());
            Assertions.assertEquals(1, fichaMedicaReply1.getFichaMedica().getNumeroFicha()
                    , "Wrong FichaMedica");

            // NombrePaciente query
            Iterator<FichaMedicaReply> fichaMedicaReplyList3 = stub.searchFichaMedica(SearchFichaMedicaReq.newBuilder()
                    .setQuery("perrowuau")
                    .build());
            FichaMedicaReply fichaMedicaReply3 = fichaMedicaReplyList3.next();
            log.debug("First FichaMedica: {}", fichaMedicaReply3.getFichaMedica());

            // Nombre duenio query
            Iterator<FichaMedicaReply> fichaMedicaReplyList4 = stub.searchFichaMedica(SearchFichaMedicaReq.newBuilder()
                    .setQuery("Sebastian")
                    .build());
            FichaMedicaReply fichaMedicaReply4 = fichaMedicaReplyList4.next();
            log.debug("First FichaMedica: {}", fichaMedicaReply4.getFichaMedica());

            // Rut String query
            Iterator<FichaMedicaReply> fichaMedicaReplyList6 = stub.searchFichaMedica(SearchFichaMedicaReq.newBuilder()
                    .setQuery("20218430-8")
                    .build());
            FichaMedicaReply fichaMedicaReply6 = fichaMedicaReplyList6.next();
            log.debug("First FichaMedica: {}", fichaMedicaReply6.getFichaMedica());
        }
    }

}
