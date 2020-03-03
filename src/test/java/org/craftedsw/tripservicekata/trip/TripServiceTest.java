package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.IUserSession;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TripServiceTest {

    @Test
    void shouldThrowException_whenUserIsNotLoggedIn() {
        TripService tripService = new TripService(new NoLoggedUserSession());
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

    @Test
    void shouldReturnTrips_whenLoggedUserIsAFriend() {
        TripService tripService = new FriendTripService();
        User user = new User();

        List<Trip> trips = tripService.getTripsByUser(user);
        Assertions.assertFalse(trips.isEmpty());
    }

    private static class NoLoggedUserSession implements IUserSession {
        @Override
        public User getLoggedUser() {
            return null;
        }
    }

    private static class LoggedUserSession implements IUserSession {
        @Override
        public User getLoggedUser() {
            return new User();
        }
    }

    private static class NotFriendTripService extends TripService {
        private NotFriendTripService() {
            super(new LoggedUserSession());
        }

        @Override
        protected boolean isFriend() {
            return false;
        }

        @Override
        protected List<Trip> getTrips(User user) {
            return new ArrayList<>();
        }
    }

    private static class FriendTripService extends TripService {
        private FriendTripService() {
            super(new LoggedUserSession());
        }

        @Override
        protected boolean isFriend() {
            return true;
        }

        @Override
        protected List<Trip> getTrips(User user) {
            ArrayList<Trip> trips = new ArrayList<>();
            trips.add(new Trip());
            return trips;
        }
    }
}