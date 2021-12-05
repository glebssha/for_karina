SELECT city, Airports_List
FROM (
         SELECT city, STRING_AGG(airport_code, ',') as Airports_List, count(1) as amnt
         FROM airports
         GROUP BY city
     ) as a
WHERE amnt > 1;