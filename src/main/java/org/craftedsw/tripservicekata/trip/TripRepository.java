package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

import java.util.List;

public class TripRepository implements ITripRepository {

    @Override
    public List<Trip> getTrip(User user) {
        List<Trip> tripList;
        tripList = TripDAO.findTripsByUser(user);
        return tripList;
    }
}
