<?php
function update_game_fields($db, $user_id, $games, $fields, $values) {
    if(count($games) != count($fields) || count($games) != count($values)) {
        outputError('games, fields, and values must match', 400);
    }
    for($i = 0; $i < count($games); $i++) {
        $db->query('DELETE FROM user_game_fields WHERE user_id="'.$user_id.'" AND game_id="'.$games[$i].'" AND field_id="'.$fields[$i].'"');
        $db->query('INSERT INTO user_game_fields(user_id, game_id, field_id, field_value) VALUES("'.$user_id.'", "'.$games[$i].'", "'.$fields[$i].'", "'.$values[$i].'")');
    }
    
    require('get_profile.php');
    get_profile($db, $user_id);
}