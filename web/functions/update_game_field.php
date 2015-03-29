<?php
function update_game_field($db, $user_id, $game, $field, $value) {
    global $LOL_RANKS_RLT;
    $db->query('DELETE FROM user_game_fields WHERE user_id="'.$user_id.'" AND game_id="'.$game.'" AND field_id="'.$field.'"');
    if($game == GAME_LOL && $field == FIELD_LOL_RANK) {
        if(!isset($LOL_RANKS_RLT[$value])) {
            outputError('Invalid rank value', 500);
        }
        $value = $LOL_RANKS_RLT[$value];
    }
    $db->query('INSERT INTO user_game_fields(user_id, game_id, field_id, field_value) VALUES("'.$user_id.'", "'.$game.'", "'.$field.'", "'.$value.'")');
    
    require('get_profile.php');
    get_profile($db, $user_id);
}