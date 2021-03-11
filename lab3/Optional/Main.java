package com.company;

import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;
public class Main {

	public static void main(String[] args) {
		City c = new City();
		c.setNume("Brasov");
		Museum v2 = new Museum();
		v2.setOpeningTime(LocalTime.of(9, 30));
		v2.setClosingTime(LocalTime.parse("17:00"));
		v2.setTicketPrice(20);
		v2.setName("Museum A");
		Hotel v1 = new Hotel();
		v1.setName("Hotel");
		v1.setRank(2);
		v1.setTicketPrice(10);
		Map<Location, Integer> map1=new HashMap<>();
		Museum v3 = new Museum();
		v3.setOpeningTime(LocalTime.of(8, 15));
		v3.setClosingTime(LocalTime.parse("18:00"));
		v3.setTicketPrice(15.5);
		v3.setName("Museum B");
		Church v4 = new Church();
		v4.setName("Church A");
		v4.setOpeningTime(LocalTime.of(6, 15));
		v4.setClosingTime(LocalTime.parse("18:00"));
		Church v5 = new Church();
		v5.setName("Church B");
		v5.setOpeningTime(LocalTime.of(6, 05));
		v5.setClosingTime(LocalTime.parse("14:00"));
		Restaurant v6 = new Restaurant();
		v6.setName("Restaurant");
		v6.setRank(3);
		v6.setTicketPrice(25);
		v1.addNewDistanceToLocation(v2,10);
		v1.addNewDistanceToLocation(v3,50);
		v2.addNewDistanceToLocation(v3,20);
		v2.addNewDistanceToLocation(v4,20);
		v2.addNewDistanceToLocation(v5,10);
		v3.addNewDistanceToLocation(v4,20);
		v3.addNewDistanceToLocation(v2,20);
		v4.addNewDistanceToLocation(v5,30);
		v4.addNewDistanceToLocation(v6,10);
		v5.addNewDistanceToLocation(v4,30);
		v5.addNewDistanceToLocation(v6,20);
		c.addLocation(v1);
		c.addLocation(v2);
		c.addLocation(v3);
		c.addLocation(v4);
		c.addLocation(v5);
		c.addLocation(v6);
		c.printLocationList();
		c.visit();
		System.out.println(v4.defaultClosing());
		System.out.println(Visitable.getVisitingDuration(v2));
		TravelPlan p= new TravelPlan();
		p.setC(c);
		p.addLocation(v1);
		p.addLocation(v2);
		p.addLocation(v3);
		TravelPlan.calculateShortestPath(c,p,v1,v3);
	}
}
