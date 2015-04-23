<?php
function update_game_field($db, $gcpm, $user_id, $game, $field, $value) {
    $db->query('DELETE FROM user_game_fields WHERE user_id="'.$user_id.'" AND game_id="'.$game.'" AND field_id="'.$field.'"');
    $db->query('INSERT INTO user_game_fields(user_id, game_id, field_id, field_value) VALUES("'.$user_id.'", "'.$game.'", "'.$field.'", "'.$value.'")');
    
    require('get_profile.php');
    get_profile($db, $user_id);
}