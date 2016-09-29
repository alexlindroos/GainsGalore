package com.example.iosdev.sensorproject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by iosdev on 27.9.2016.
 */

public class DiscountCreator {

    public Set<String> discounts = new HashSet<>(20);

    public void makeDiscounts() {
        String d1 = "Free coffee from the Metropolia Unicafe - MUC123";
        discounts.add(d1);

        String d2 = "50% off from Luhta winter jackets from the Intersport - IL912";
        discounts.add(d2);

        String d3 = "25% off from any food you order in Amarillo - A3139";
        discounts.add(d3);

        String d4 = "Helsingin Sanomat for 6 months only 20,00€ - HS0900";
        discounts.add(d4);

        String d5 = "Mens haircut only 12€ in Style Workshop Kruununhaka - SWK2922";
        discounts.add(d5);

        String d6 = "Free car wash in Koskelan Autopesu - KAP8889";
        discounts.add(d6);

        String d7 = "Chefs menu 10€ in Töölön Sävel - TS1231";
        discounts.add(d7);

        String d8 = "Free Gym membership in Fitness 24/7 - F1223";
        discounts.add(d8);

        String d9 = "Exit room game for 1-6 people only 9€ in Exit Room Helsinki - ER5582";
        discounts.add(d9);

        String d10 = "Free bucket from Tokmanni - FB9942";
        discounts.add(d10);


    }
}
