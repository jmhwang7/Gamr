<?php
/**
# Source: http://zcentric.com/2010/03/11/calculate-distance-in-mysql-with-latitude-and-longitude/comment-page-1/#comment-783
# Sample usage: select get_distance_in_miles_between_geo_locations(-34.017330, 22.809500, latitude, longitude) as distance_from_input from places;
DELIMITER $$

DROP FUNCTION IF EXISTS `get_distance_in_miles_between_geo_locations` $$
CREATE FUNCTION get_distance_in_miles_between_geo_locations(geo1_latitude decimal(10,6), geo1_longitude decimal(10,6), geo2_latitude decimal(10,6), geo2_longitude decimal(10,6)) 
returns decimal(10,3) DETERMINISTIC
BEGIN
  return ((ACOS(SIN(geo1_latitude * PI() / 180) * SIN(geo2_latitude * PI() / 180) + COS(geo1_latitude * PI() / 180) * COS(geo2_latitude * PI() / 180) * COS((geo1_longitude - geo2_longitude) * PI() / 180)) * 180 / PI()) * 60 * 1.1515);
END $$

DELIMITER ;

*/
function match($db, $user_id, $use_location, $use_games) {
    if(!$use_location && !$use_games) {
        outputError('Either use_location or use_games must be specified', 400);
    }
    
    // Look up the current user
    $user = $db->queryRow('SELECT * FROM users WHERE id="'.$user_id.'" LIMIT 1');
    if($user == null) {
        outputError('Invalid user ID', 400);
    }
    $userLat = $user['lat'];
    $userLon = $user['lon'];
    
    // Set up query depending on parameters
    $whereClause = '';
    $havingClause = '';
    if($use_location) {
        $havingClause .= ' distance < 200';
    }
    if($use_games) {
        // TODO add game matching logic here
        $whereClause .= '';
    }

    $result = $db->query('SELECT id, lat, lon, 
    get_distance_in_miles_between_geo_locations("'.$userLat.'", "'.$userLon.'", lat, lon) AS distance
    FROM users
    WHERE id != "'.$user_id.'"
    '.$whereClause.'
    '.($havingClause == '' ? '' : 'HAVING'.$havingClause).'
    ORDER BY distance ASC
   ');


   $users = array();
    while($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

    outputResponse($users);
}
?>