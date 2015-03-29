<?php
function get_profile($db, $user_id) {
    global $LOL_RANKS;
    $output = array();

    $user = $db->queryRow('SELECT * FROM users WHERE id="'.$user_id.'"');
    $output['username'] = $user['username'];
    
    $output['games'] = array();
    $result = $db->query('SELECT * FROM user_games WHERE user_id="'.$user_id.'"');
    while($row = $result->fetch_assoc()) {
        $output['games'][$row['game_id']] = array(
            'in_game_name' => $row['in_game_name'],
            'game_fields' => array()
        );
    }

    $result = $db->query('SELECT * FROM user_game_fields WHERE user_id="'.$user_id.'"');
    while($row = $result->fetch_assoc()) {
        if($row['game_id'] == GAME_LOL && $row['field_id'] == FIELD_LOL_RANK) {
            $row['field_value'] = $LOL_RANKS[$row['field_value']];
        }
        $output['games'][$row['game_id']]['game_fields'][$row['field_id']] = $row['field_value'];
    }
    
    outputResponse($output);
}