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

import cl.ucn.disc.pdis.fivet.grpc.AddControlReq;
import cl.ucn.disc.pdis.fivet.grpc.AddFichaMedicaReq;
import cl.ucn.disc.pdis.fivet.grpc.AddPersonaReq;
import cl.ucn.disc.pdis.fivet.grpc.AutenticateReq;
import cl.ucn.disc.pdis.fivet.grpc.ControlEntity;
import cl.ucn.disc.pdis.fivet.grpc.FichaMedicaEntity;
import cl.ucn.disc.pdis.fivet.grpc.FichaMedicaReply;
import cl.ucn.disc.pdis.fivet.grpc.FivetServiceGrpc;
import cl.ucn.disc.pdis.fivet.grpc.PersonaReply;
import cl.ucn.disc.pdis.fivet.grpc.RetrieveFichaMedicaReq;
import cl.ucn.disc.pdis.fivet.grpc.SearchFichaMedicaReq;
import cl.ucn.disc.pdis.fivet.model.Control;
import cl.ucn.disc.pdis.fivet.model.FichaMedica;
import cl.ucn.disc.pdis.fivet.model.Persona;
import com.google.protobuf.Any;
import com.google.rpc.Code;
import com.google.rpc.ErrorInfo;
import com.google.rpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


/**
 * The fivet service impl.
 */
@Slf4j
public class FivetServiceImpl extends FivetServiceGrpc.FivetServiceImplBase {

    /**
     * The controller.
     */
    private final FivetController fivetController;

    /**
     * Fivet service impl instance.
     *
     * @param databaseUrl to use.
     */
    public FivetServiceImpl(String databaseUrl) throws SQLException {
        this.fivetController = new FivetControllerImpl(databaseUrl, true);
    }

    /**
     * Exception builder.
     *
     * @param code to use
     * @param message to send
     * @return a exception
     */
    private StatusRuntimeException buildException(final Code code, final String message) {
        return StatusProto.toStatusRuntimeException(Status.newBuilder()
                .setCode(code.getNumber())
                .setMessage(message)
                .addDetails(Any.pack(ErrorInfo.newBuilder()
                        .setReason(message)
                        .build()))
                .build());
    }

    /**
     * autenticate.
     *
     * @param request to use, contains a login, password
     * @param responseObserver to use
     */
    public void autenticate(AutenticateReq request, StreamObserver<PersonaReply> responseObserver) {
        // Retrieve from Controller
        Optional<Persona> persona = this.fivetController.autenticar(request.getLogin(), request.getPassword());
        if (persona.isPresent()) {
            // Return the observer
            responseObserver.onNext(PersonaReply.newBuilder()
                    .setPersona(ModelAdapter.build(persona.get()))
                    .build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(buildException(Code.PERMISSION_DENIED, "Wrong Credentials"));
        }
    }

    /**
     * add a control.
     *
     * @param request to use, contains a Control
     * @param responseObserver to use
     */
    public void addControl(AddControlReq request, StreamObserver<FichaMedicaReply> responseObserver) {
        Optional<FichaMedica> fichaMedica = this.fivetController.getFichaMedica(request
                .getControl().getFichaMedica().getNumeroFicha());
        // Check if the FichaMedica asociated exists in the system
        if (fichaMedica.isPresent()) {
            // ControlEntityAux
            ControlEntity cEA = ControlEntity.newBuilder().build();
            // Check if the attributes required are not null and add the control
            if (request.getControl().getAltura() != cEA.getAltura()
                    && request.getControl().getDiagnostico() != cEA.getDiagnostico()
                    && request.getControl().getFecha() != cEA.getFecha()
                    && request.getControl().getPeso() != cEA.getPeso()
                    && request.getControl().getTemperatura() != cEA.getTemperatura()
                    && request.getControl().getVeterinario() != cEA.getVeterinario()) {
                Control control = ModelAdapter.build(request.getControl());
                this.fivetController.addControl(control);
                responseObserver.onNext(FichaMedicaReply.newBuilder().setFichaMedica(ModelAdapter
                                .build(fichaMedica.get()))
                        .build());
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(buildException(Code.INVALID_ARGUMENT, "Invalid argument"));
            }
        } else {
            responseObserver.onError(buildException(Code.INVALID_ARGUMENT, "Wrong fichaMedica on control"));
        }
    }

    /**
     * retrieve a FichaMedica.
     *
     * @param request to use, contains a NumeroFicha
     * @param responseObserver to use
     */
    public void retrieveFichaMedica(RetrieveFichaMedicaReq request,
                                    StreamObserver<FichaMedicaReply> responseObserver) {
        Optional<FichaMedica> fichaMedica = this.fivetController.getFichaMedica(request.getNumeroFicha());
        // Check if the fichaMedica exists
        if (fichaMedica.isPresent()) {
            responseObserver.onNext(FichaMedicaReply.newBuilder().setFichaMedica(ModelAdapter.build(fichaMedica.get()))
                    .build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(buildException(Code.NOT_FOUND, "FichaMedica Not Found"));
        }
    }

    /**
     * Search a FichaMedica by a q.
     *
     * @param request to use, contains a q
     * @param responseObserver to use
     */
    public void searchFichaMedica(SearchFichaMedicaReq request, StreamObserver<FichaMedicaReply> responseObserver) {

        Collection<FichaMedica> fichasMedicasEncontradas = new ArrayList<>();
        Collection<FichaMedica> fichasMedicasBD = this.fivetController.getAllFichaMedica();
        // Search if the query is a number
        if (NumberUtils.isCreatable(request.getQuery())) {
            // search by numeroFicha
            Optional<FichaMedica> fichaMedicaNumero = this.fivetController
                    .getFichaMedica(Integer.valueOf(request.getQuery()));
            fichaMedicaNumero.ifPresent(fichasMedicasEncontradas::add);
        }
        // Search by coincidences on the Duenio rut
        for (FichaMedica fichaMedica : fichasMedicasBD) {
            if (fivetController.stringMatch(request.getQuery(), fichaMedica.getDuenio().getRut())) {
                fichasMedicasEncontradas.add(fichaMedica);
            }
        }
        // Search by nombrePaciente
        for (FichaMedica fichaMedica : fichasMedicasBD) {
            if (fivetController.stringMatch(request.getQuery(), fichaMedica.getNombrePaciente())) {
                fichasMedicasEncontradas.add(fichaMedica);
            }
        }
        // Search by name of the duenio
        for (FichaMedica fichaMedica : fichasMedicasBD) {
            if (fivetController.stringMatch(request.getQuery(), fichaMedica.getDuenio().getNombre())) {
                fichasMedicasEncontradas.add(fichaMedica);
            }
        }
        // Check if fichaMedica were founded
        if (fichasMedicasEncontradas.size() > 0) {
            // Filter repeated FichaMedica founded
            Collection<FichaMedica> fichaMedicaFiltradas = fivetController
                    .filterRepeatedFichaMedica(fichasMedicasEncontradas);
            for (FichaMedica fichaMedica : fichaMedicaFiltradas) {
                responseObserver.onNext(FichaMedicaReply.newBuilder().setFichaMedica(ModelAdapter.build(fichaMedica))
                        .build());
                responseObserver.onCompleted();
            }
        }
    }

    /**
     * Add a FichaMedica.
     *
     * @param request to use, contains a Control
     * @param responseObserver to use
     */
    public void addFichaMedica(AddFichaMedicaReq request, StreamObserver<FichaMedicaReply> responseObserver) {
        // FichaMedicaAux
        FichaMedicaEntity fMA = FichaMedicaEntity.newBuilder().build();
        // Check if the attributes required are not null and add the FichaMedica
        if (request.getFichaMedica().getNumeroFicha() != fMA.getNumeroFicha()
                && request.getFichaMedica().getNombrePaciente() != fMA.getNombrePaciente()
                && request.getFichaMedica().getTipo() != fMA.getTipo()
                && request.getFichaMedica().getSexo() != fMA.getSexo()
                && request.getFichaMedica().getRaza() != fMA.getRaza()
                && request.getFichaMedica().getColor() != fMA.getColor()
                && request.getFichaMedica().getEspecie() != fMA.getEspecie()
                && request.getFichaMedica().getFechaNacimiento() != fMA.getFechaNacimiento()) {
            FichaMedica fichaMedica = ModelAdapter.build(request.getFichaMedica());
            // Check if the FichaMedica already exists
            if (!fivetController.getFichaMedica(fichaMedica.getNumeroFicha()).isPresent()) {
                this.fivetController.addFichaMedica(fichaMedica);
                responseObserver.onNext(FichaMedicaReply.newBuilder().setFichaMedica(request.getFichaMedica()).build());
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(buildException(Code.ALREADY_EXISTS, "FichaMedica already exists"));
            }
        } else {
            responseObserver.onError(buildException(Code.INVALID_ARGUMENT, "Invalid argument"));
        }
    }

    /**
     * add a Persona to the system.
     *
     * @param request contains a Persona
     * @param responseObserver to use
     */
    public void addPersona(AddPersonaReq request, StreamObserver<PersonaReply> responseObserver) {
        Persona persona = ModelAdapter.build(request.getPersona());
        // Check if the attributes required are valid
        if (!persona.getRut().isEmpty() && !persona.getEmail().isEmpty() && !persona.getNombre().isEmpty()) {
            // Check if the Persona already exists
            if (!fivetController.retrieveByLogin(persona.getRut()).isPresent()
                    && !fivetController.retrieveByLogin(persona.getEmail()).isPresent()) {
                this.fivetController.addPersona(persona, "a");
                responseObserver.onNext(PersonaReply.newBuilder().setPersona(request.getPersona()).build());
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(buildException(Code.ALREADY_EXISTS, "Persona already exists"));
            }
        } else {
            responseObserver.onError(buildException(Code.INVALID_ARGUMENT, "Invalid argument"));
        }
    }
}
