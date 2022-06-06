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

import cl.ucn.disc.pdis.fivet.grpc.*;
import cl.ucn.disc.pdis.fivet.orm.LocalDateType;
import cl.ucn.disc.pdis.fivet.orm.ZonedDateTimeType;
import cl.ucn.disc.pdis.fivet.services.FivetController;
import cl.ucn.disc.pdis.fivet.services.FivetControllerImpl;
import com.j256.ormlite.field.DataPersisterManager;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * The server of Fivet
 */
@Slf4j
public class FivetServer {

    /**
     * The Main
     * @param args to use
     */
    @SneakyThrows({InterruptedException.class, IOException.class})
    public static void main(String[] args) throws SQLException {

        // Registering the LocalDateType
        DataPersisterManager.registerDataPersisters(LocalDateType.INSTANCE);

        // Registering the ZonedDateTimeType
        DataPersisterManager.registerDataPersisters(ZonedDateTimeType.INSTANCE);

        log.debug("Building the FivetServiceImpl ...");
        FivetServiceImpl fivetService = new FivetServiceImpl("jdbc:h2:mem:fivet");

        log.debug("Building and Starting the server ..");

        // Build and start the server
        Server server = ServerBuilder.forPort(8080)
                .addService(fivetService)
                .build()
                .start();

        // Awaits
        server.awaitTermination();

        log.debug("Done.");

    }

    @Slf4j
    private static class FivetServiceImpl extends FivetServiceGrpc.FivetServiceImplBase {

        /**
         * The controller.
         */
        private final FivetController fivetController;

        /**
         *
         * @param databaseUrl to use.
         * @throws SQLException
         */
        public FivetServiceImpl(String databaseUrl) throws SQLException {
            this.fivetController = new FivetControllerImpl(databaseUrl, true);
        }

        public void autenticate(AutenticateReq request, StreamObserver<PersonaReply> responseObserver) {
            // Retrieve from Controller
            Optional<cl.ucn.disc.pdis.fivet.model.Persona> persona = this.fivetController
                    .retrieveLogin(request
                    .getLogin());
            if (persona.isPresent()) {
                // Return the observer
                responseObserver.onNext(cl.ucn.disc.pdis.fivet.grpc.PersonaReply.newBuilder()
                        .setPersona(PersonaEntity.newBuilder()
                                .setNombre("Sebastian")
                                .setRut("202184308")
                                .setEmail("seba@gmail.com")
                                .setDireccion("micasa #3332")
                                .build())
                        .build());
                responseObserver.onCompleted();
            }
            else {
                responseObserver.onNext(cl.ucn.disc.pdis.fivet.grpc.PersonaReply.newBuilder()
                        .setPersona(PersonaEntity.newBuilder()
                                .setNombre("Sebastian")
                                .setRut("202184308")
                                .setEmail("seba@gmail.com")
                                .setDireccion("micasa #3332")
                                .build())
                        .build());
                responseObserver.onCompleted();
            }
        }

        public void addControl(AddControlReq request) {

        }

        public void retrieveFichaMedica(RetrieveFichaMedicaReq request) {

        }

        public void searchFichaMedica(SearchFichaMedicaReq request) {

        }

        public void addFichaMedica(AddFichaMedicaReq request) {

        }



    }

}



