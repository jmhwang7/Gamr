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
function match($db, $user_id, $use_location, $use_games, $count) {
    global $LOL_RANKS;
    
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
    
    // Look up their game fields
    $userFields = array();
    $userFieldsResult = $db->query('SELECT field_id, field_value FROM user_game_fields WHERE user_id="'.$user_id.'"');
    
    $userFieldsQuery = array();
    while($row = $userFieldsResult->fetch_assoc()) {
        $userFields[$row['field_id']] = $row['field_value'];
        $userFieldsQuery[$row['field_id']] = '(';
        $values = explode(',', $row['field_value']);
        for($i = 0; $i < count($values); $i++) {
            $userFieldsQuery[$row['field_id']] .= 'FIND_IN_SET("'.$db->escapeString($values[$i]).'", field_value)';
            if($i < count($values)-1) {
                $userFieldsQuery[$row['field_id']] .= ' OR ';
            }
        }
        $userFieldsQuery[$row['field_id']] .= ')';
    }
    if(count($userFields) != 3) {
        $use_games = false;
        $use_location = true;
    }
    
    // Set up query depending on parameters
    $havingClause = '';
    if($use_location && $use_games) {
        $havingClause .= ' distance < '.MAX_DISTANCE;
        $order = 'score DESC';
    } else if($use_location) {
        $havingClause .= ' distance < '.MAX_DISTANCE;
        $order = 'distance_score DESC';
    } else if($use_games) {
        $order = 'game_score DESC';        
    }
        
    $result = $db->query('SELECT username, id, distance,
    1 - distance / '.MAX_DISTANCE.' AS distance_score, 
    AVG(game_field_score) AS game_score,
    (1 - distance / '.MAX_DISTANCE.') * AVG(game_field_score) AS score,
    role, rank, gamemode
    FROM
    (
        SELECT username, id, lat, lon, field_id, field_value,
        get_distance_in_miles_between_geo_locations("'.$userLat.'", "'.$userLon.'", lat, lon) AS distance,
        '.($use_games ? 'if(field_id = '.FIELD_LOL_ROLE.', if('.$userFieldsQuery[FIELD_LOL_ROLE].', 0, 1), 
            if(field_id = '.FIELD_LOL_RANK.', (1-ABS(field_value - '.$userFields[FIELD_LOL_RANK].')*0.5), 
                if(field_id = '.FIELD_LOL_GAMEMODE.', if('.$userFieldsQuery[FIELD_LOL_GAMEMODE].', 1, 0), NULL)
            )
        )' : '1').' AS game_field_score,
        (SELECT field_value FROM user_game_fields WHERE user_id=id AND field_id = '.FIELD_LOL_RANK.') AS rank,
        (SELECT field_value FROM user_game_fields WHERE user_id=id AND field_id = '.FIELD_LOL_ROLE.') AS role,
        (SELECT field_value FROM user_game_fields WHERE user_id=id AND field_id = '.FIELD_LOL_GAMEMODE.') AS gamemode,
        (SELECT COUNT(*) FROM matches WHERE user_id="'.$user_id.'" AND other_user_id=id) AS user_matched_or_passed,
        (SELECT COUNT(*) FROM matches WHERE user_id=id AND other_user_id="'.$user_id.'" AND matched=0) AS other_passed
        FROM users LEFT JOIN user_game_fields ON users.id=user_game_fields.user_id
        WHERE id != "'.$user_id.'"
        '.($havingClause == '' ? '' : 'HAVING'.$havingClause).'
    ) AS data
    WHERE user_matched_or_passed=0 AND other_passed=0
    GROUP BY id
    HAVING game_score >= 0
    ORDER BY '.$order.'
    LIMIT '.$count.'
   ');


   $users = array();
    while($row = $result->fetch_assoc()) {
        $users[] = array(
            'id' => $row['id'],
            'username' => $row['username'],
            'distance' => $row['distance'],
            'role' => explode(',', $row['role']),
            'rank' => array($LOL_RANKS[$row['rank']]),
            'gamemode' => explode(',', $row['gamemode'])
        );
    }

    outputResponse($users);
}
?>