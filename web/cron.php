<?php
// This file is automatically executed every day
define('DEBUG', 1);
require('include/config.php');
require('include/response.php');
require('include/errorHandler.php');
require('include/db.php');
require('include/riotApi.php');
require('functions/update_game_field.php');

$result = $db->query('SELECT id,in_game_name FROM users LEFT JOIN user_games ON users.id = user_games.user_id WHERE game_id='.GAME_LOL);
while($row = $result->fetch_assoc()) {
    $in_game_name = $row['in_game_name'];
    $user_id = $row['id'];
    $rankString = getRank('na', $in_game_name);
    if(isset($LOL_RANKS_RLT[$rankString])) {
        $rank = $LOL_RANKS_RLT[$rankString];
        $db->query('DELETE FROM user_game_fields WHERE user_id="'.$user_id.'" AND game_id="'.GAME_LOL.'" AND field_id="'.FIELD_LOL_RANK.'"');
        $db->query('INSERT INTO user_game_fields(user_id, game_id, field_id, field_value) VALUES("'.$user_id.'", "'.GAME_LOL.'", "'.FIELD_LOL_RANK.'", "'.$rank.'")');
    }
    sleep(1);
}
?>