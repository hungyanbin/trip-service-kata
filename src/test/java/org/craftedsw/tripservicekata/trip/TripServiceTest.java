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
        User loggedUser = new User();
        TripService tripService = new TripService(
                new FakeUserSession(loggedUser),
                new FakeTripRepository()
        );
        User user = new User();

        List<Trip> trips = tripService.getTripsByUser(user);
        Assertions.assertTrue(trips.isEmpty());
    }

    @Test
    void shouldReturnTrips_whenLoggedUserIsAFriend() {
        User loggedUser = new User();
        User user = new User();
        user.addFriend(loggedUser);
        ArrayList<Trip> fakeTrips = new ArrayList<>();
        fakeTrips.add(new Trip());

        TripService tripService = new TripService(
                new FakeUserSession(loggedUser),
                new FakeTripRepository(fakeTrips)
        );

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

    private static class FakeUserSession implements IUserSession {

        private User user;

        private FakeUserSession(User user) {
            this.user = user;
        }

        @Override
        public User getLoggedUser() {
            return user;
        }
    }
}