SELECT city, count(1) as Cancelled_num
FROM flights INNER JOIN airports
                        ON flights.departure_airport = airports.airport_code
WHERE flights.status = 'Cancelled'
GROUP BY city ORDER BY Cancelled_num DESC LIMIT 5;