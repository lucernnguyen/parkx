package org.parkz.modules.parking_session.factory.app;

import org.parkz.modules.parking_session.factory.IParkingSessionFactory;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.model.request.ConfirmCheckInRequest;
import org.parkz.modules.parking_session.model.request.ConfirmCheckOutRequest;
import org.parkz.modules.parking_session.model.request.CreateParkingSessionRequest;
import org.parkz.modules.parking_session.model.request.InitCheckoutRequest;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;

import java.util.UUID;

public interface IAppParkingSessionFactory extends IParkingSessionFactory {

    ParkingSessionInfo checkin(CreateParkingSessionRequest request) throws InvalidException;

    ParkingSessionInfo getSessionInfo(UUID sessionId) throws InvalidException;

    SuccessResponse confirmCheckIn(ConfirmCheckInRequest request) throws InvalidException;

    SuccessResponse initCheckout(InitCheckoutRequest request) throws InvalidException;

    SuccessResponse checkOut(ConfirmCheckOutRequest request) throws InvalidException;
}
