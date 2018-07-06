-- (1) Write MySQL query to find IPs that made more than a certain number of requests for a given time period.
-- Ex: Write SQL to find IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00.
USE WALLETHUB_DB;
SET @startDate= '2017-01-01 13:00:00';
SET @endDate= '2017-01-01 14:00:00';
SET @threshold= 100;

SELECT 
ip AS IpAddress,
COUNT(ip) AS Frequency
FROM Log
WHERE date BETWEEN @startDate AND @endDate
GROUP BY IpAddress
HAVING Frequency >= @threshold
ORDER BY Frequency DESC;

