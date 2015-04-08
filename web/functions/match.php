<?php
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
        $field = $row['field_id'];
        $value = $row['field_value'];
        $userFields[$field] = $value;
        $userFieldsQuery[$field] = '(';
        $values = explode(',', $value);
        for($i = 0; $i < count($values); $i++) {
            if($field == FIELD_LOL_GAMEMODE) { // Game mode: at least 1 must match
                $userFieldsQuery[$field] .= 'FIND_IN_SET("'.$db->escapeString($values[$i]).'", field_value)';
            } else if($field == FIELD_LOL_ROLE) {// Role: at least 1 must be different
                $userFieldsQuery[$field] .= '!FIND_IN_SET("'.$db->escapeString($values[$i]).'", field_value)';                
            }
            if($i < count($values)-1) {
                $userFieldsQuery[$field] .= ' OR ';
            }
        }
        $userFieldsQuery[$field] .= ')';
    }
    if(count($userFields) != 4) {
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
        '.($use_games ? 'if(field_id = '.FIELD_LOL_ROLE.', if('.$userFieldsQuery[FIELD_LOL_ROLE].', 1, 0), 
            if(field_id = '.FIELD_LOL_RANK.', '.($userFields[FIELD_LOL_RANK] == 0 ? 0.5 : '(1-ABS(field_value - '.$userFields[FIELD_LOL_RANK].')*0.5)').', 
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
            'rank' => array(isset($row['rank']) ? $LOL_RANKS[$row['rank']] : ''),
            'gamemode' => explode(',', $row['gamemode'])
        );
    }

    outputResponse($users);
}
?>