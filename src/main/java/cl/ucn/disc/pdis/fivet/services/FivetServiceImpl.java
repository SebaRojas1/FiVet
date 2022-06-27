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

import cl.ucn.disc.pdis.fivet.grpc.AutenticateReq;
import cl.ucn.disc.pdis.fivet.grpc.FivetServiceGrpc;
import cl.ucn.disc.pdis.fivet.grpc.PersonaEntity;
import cl.ucn.disc.pdis.fivet.grpc.PersonaReply;
import cl.ucn.disc.pdis.fivet.model.Persona;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class FivetServiceImpl extends FivetServiceGrpc.FivetServiceImplBase {

    /**
     * The controller.
     */
    private final FivetController fivetController;

    /**
     * @param databaseUrl to use.
     * @throws SQLException
     */
    public FivetServiceImpl(String databaseUrl) throws SQLException {
        this.fivetController = new FivetControllerImpl(databaseUrl, true);
    }

    public void autenticate(AutenticateReq request, StreamObserver<PersonaReply> responseObserver) {
        // Retrieve from Controller
        Optional<Persona> persona = this.fivetController
                .retrieveLogin(request
                        .getLogin());
        if (persona.isPresent()) {
            // Return the observer
            responseObserver.onNext(PersonaReply.newBuilder()
                    .setPersona(PersonaEntity.newBuilder()
                            .setNombre(persona.get().getNombre())
                            .setRut(persona.get().getRut())
                            .setEmail(persona.get().getEmail())
                            .setDireccion(persona.get().getDireccion())
                            .build())
                    .build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onNext(PersonaReply.newBuilder()
                    .setPersona(PersonaEntity.newBuilder()
                            .setNombre("Sebastian")
                            .setRut("202184308")
                            .setEmail("seba@gmail.com")
                            .setDireccion("micasa #3332")
                            .build())
                    .build());
            responseObserver.onCompleted();
            //responseObserver.onError(buildException(Code.PERMISSION_DENIED, "Wrong Credentials"));
        }
    }

        /*public void addControl(AddControlReq request, StreamObserver<FichaMedicaReply> responseObserver) {
            Optional<cl.ucn.disc.pdis.fivet.model.FichaMedica> fichaMedica = this.fivetController.getFichaMedica(request
                    .getControl().getFichaMedica().getNumeroFicha());
            if (fichaMedica.isPresent()) {
                Control control = Control.builder()
                        .altura(Double.valueOf(request.getControl().getAltura()))
                        .diagnostico(request.getControl().getDiagnostico())
                        .temperatura(Double.valueOf(request.getControl().getTemperatura()))
                        .peso(Double.valueOf(request.getControl().getTemperatura()))
                        .fecha(ZonedDateTime.parse(request.getControl().getFecha()))
                        .fichaMedica(request.getControl().getFichaMedica().get)
                        .controles
                        .build();
                this.fivetController.addControl();
            }
            else {

            }
        }*/

    /*public void retrieveFichaMedica(RetrieveFichaMedicaReq request,
                                    StreamObserver<FichaMedicaReply> responseObserver) {

    }*/

    /*public void searchFichaMedica(SearchFichaMedicaReq request, StreamObserver<FichaMedicaReply> responseObserver) {

    }*/

    /*public void addFichaMedica(AddFichaMedicaReq request, StreamObserver<FichaMedicaReply> responseObserver) {
        FichaMedica fichaMedica = FichaMedica.builder().numeroFicha(request.getFichaMedica().getNumeroFicha())
                .raza(request.getFichaMedica().getRaza())
                .tipo(request.getFichaMedica().getTipo())
                .duenio(Persona.builder().rut(request.getFichaMedica().getDuenio().getRut())
                        .nombre(request.getFichaMedica().getDuenio().getNombre())
                        .direccion(request.getFichaMedica().getDuenio().getDireccion())
                        .email(request.getFichaMedica().getDuenio().getEmail())
                        .build())
                .fechaNacimiento(LocalDate.parse(request.getFichaMedica().getFechaNacimiento()))
                .especie(request.getFichaMedica().getEspecie())
                .color(request.getFichaMedica().getColor())
                .nombrePaciente(request.getFichaMedica().getNombrePaciente())
                .sexo(Enum.valueOf(FichaMedica.Sexo.class, request.getFichaMedica().getSexo().name()))
                .build();
        this.fivetController.addFichaMedica(fichaMedica);
        responseObserver.onNext(FichaMedicaReply.newBuilder().setFichaMedica(request.getFichaMedica()).build());
        responseObserver.onCompleted();
    }*/



    /*public static PersonaReply buildPersona (Persona persona) {

    }*/

    /*public static FichaMedicaReply buildPersona (Persona persona) {

    }*/

}
