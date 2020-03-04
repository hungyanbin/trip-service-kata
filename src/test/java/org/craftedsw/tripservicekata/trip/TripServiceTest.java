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
        TripService tripService = new TripService(new NoLoggedUserSession(), new FakeTripRepository());
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
        User loggedUser = new User();
        User user = new User();
        user.addFriend(loggedUser);
        TripService tripService = new FriendTripService(loggedUser);

        List<Trip> trips = tripService.getTripsByUser(user);
        Assertions.assertFalse(trips.isEmpty());
    }

    private static class NoLoggedUserSession implements IUserSession {
        @Override
        public User getLoggedUser() {
            return null;
        }
    }

    private static class FakeTripRepository implements ITripRepository {

        private List<Trip> trips;

        private FakeTripRepository(List<Trip> trips) {
            this.trips = trips;
        }

        public FakeTripRepository() { }

        @Override
        public List<Trip> getTrip(User user) {
            return trips;
        }
    }

    private static class LoggedUserSession implements IUserSession {

        private User user = new User();

        private LoggedUserSession(User user) {
            this.user = user;
        }

        public LoggedUserSession() { }

        @Override
        public User getLoggedUser() {
            return user;
        }
    }

    private static class NotFriendTripService extends TripService {
        private NotFriendTripService() {
            super(new LoggedUserSession(), new FakeTripRepository());
        }

        @Override
        protected List<Trip> getTrips(User user) {
            return new ArrayList<>();
        }
    }

    private static class FriendTripService extends TripService {
        private FriendTripService(User user) {
            super(new LoggedUserSession(user), new FakeTripRepository());
        }

        @Override
        protected List<Trip> getTrips(User user) {
            ArrayList<Trip> trips = new ArrayList<>();
            trips.add(new Trip());
            return trips;
        }
    }
}