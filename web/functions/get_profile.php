<?php
function get_profile($db, $gcpm, $user_id) {
    global $LOL_RANKS;
    $output = array();

    $user = $db->queryRow('SELECT * FROM users WHERE id="'.$user_id.'"');
    $output['username'] = $user['username'];
    
    $result = $db->query('SELECT * FROM user_games WHERE user_id="'.$user_id.'"');
    while($row = $result->fetch_assoc()) {
        $output[$row['game_id']] = array(
            'in_game_name' => $row['in_game_name'],
        );
    }

    $result = $db->query('SELECT * FROM user_game_fields WHERE user_id="'.$user_id.'"');
    while($row = $result->fetch_assoc()) {
        if($row['game_id'] == GAME_LOL && $row['field_id'] == FIELD_LOL_RANK) {
            $row['field_value'] = $LOL_RANKS[$row['field_value']];
        }
        $output[$row['game_id']][$row['field_id']] = explode(',', $row['field_value']);
    }
    
    outputResponse($output);
}