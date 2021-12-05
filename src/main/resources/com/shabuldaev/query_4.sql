SELECT MONTH(scheduled_departure) as Mnth, count(*) as Cancelled
FROM flights
WHERE status = 'Cancelled'
GROUP BY Mnth;