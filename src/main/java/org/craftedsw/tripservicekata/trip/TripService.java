package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.IUserSession;
import org.craftedsw.tripservicekata.user.User;

public class TripService {

	private IUserSession userSession;

	public TripService(IUserSession userSession) {
		this.userSession = userSession;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedUser = userSession.getLoggedUser();
		boolean isFriend = isFriend();
		if (loggedUser != null) {
			for (User friend : user.getFriends()) {
				if (friend.equals(loggedUser)) {
					isFriend = true;
					break;
				}
			}
			if (isFriend) {
				tripList = getTrips(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}

	protected boolean isFriend() {
		return false;
	}

	protected List<Trip> getTrips(User user) {
		List<Trip> tripList;
		tripList = TripDAO.findTripsByUser(user);
		return tripList;
	}
}
