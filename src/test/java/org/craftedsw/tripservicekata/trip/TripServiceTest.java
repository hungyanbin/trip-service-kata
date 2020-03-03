package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TripServiceTest {

    @Test
    void shouldThrowException_whenUserIsNotLoggedIn() {
        TripService tripService = new NotLoggedInTripService();
        User user = new User();

        Assertions.assertThrows(UserNotLoggedInException.class,
                () -> tripService.getTripsByUser(user));
    }

    @Test
    void shouldNotReturnTrips_whenLoggedUserIsNotAFriend() {
        TripService tripService = new NotFriendTripService();
        User user = new User();

        List<Trip> trips = tripService.getTripsByUser(user);
        Assertions.assertTrue(trips.isEmpty());
    }

    private static class NotLoggedInTripService extends TripService {
        @Override
        protected User getLoggedUser() {
            return null;
        }
    }

    private static class NotFriendTripService extends TripService {
        @Override
        protected boolean isFriend() {
            return false;
        }

        @Override
        protected List<Trip> getTrips(User user) {
            return new ArrayList<>();
        }

        @Override
        protected User getLoggedUser() {
            return new User();
        }
    }
}