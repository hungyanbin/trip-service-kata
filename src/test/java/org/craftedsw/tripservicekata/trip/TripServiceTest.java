package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TripServiceTest {

    @Test
    void shouldThrowException_whenUserIsNotLoggedIn() {
        TripService tripService = new NotLoggedInTripService();
        User user = new User();

        Assertions.assertThrows(UserNotLoggedInException.class,
                () -> tripService.getTripsByUser(user));
    }

    private static class NotLoggedInTripService extends TripService {
        @Override
        protected User getLoggedUser() {
            return null;
        }
    }
}