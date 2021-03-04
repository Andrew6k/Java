package com.company;

import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;
public class Main {

	public static void main(String[] args) {
		Museum m = new Museum();
		m.setOpeningTime(LocalTime.of(9, 30));
		m.setClosingTime(LocalTime.parse("17:00"));
		m.setTicketPrice(20);
		m.setName("A");
		Hotel v1 = new Hotel();
		v1.setName("Hotel");
		v1.setRank(2);
		v1.setTicketPrice(10);
		Map<Location, Integer> map1=new HashMap<>();
		Museum m1 = new Museum();
		m1.setOpeningTime(LocalTime.of(8, 15));
		m1.setClosingTime(LocalTime.parse("18:00"));
		m1.setTicketPrice(15.5);
		m1.setName("B");
		Church c1 = new Church();
		c1.setName("Church A");
		c1.setOpeningTime(LocalTime.of(6, 15));
		c1.setClosingTime(LocalTime.parse("18:00"));
		Church c2 = new Church();
		c2.setName("Church B");
		c2.setOpeningTime(LocalTime.of(6, 45));
		c2.setClosingTime(LocalTime.parse("14:00"));
		Restaurant r1 = new Restaurant();
		r1.setName("Restaurant");
		r1.setRank(3);
		r1.setTicketPrice(25);
		map1.put(m,10);
		map1.put(m1,50);
		v1.setCost(map1);

	}
}
