-- (2) Write MySQL query to find requests made by a given IP.
USE WALLETHUB_DB;
SET @ip= '192.168.77.101';

SELECT *
FROM Log
WHERE ip = @ip
ORDER BY date ASC;