<?php
function update_profile($db, $user_id, $username, $game, $in_game_name) {
    $exists = $db->query('SELECT id FROM users WHERE id="'.$user_id.'" LIMIT 1')->num_rows;
    if(!$exists) {
        if($username != null) {
            $db->query('INSERT INTO users(id, username) VALUES("'.$user_id.'", "'.($db->escapeString($username)).'")');
        } else {
            $db->query('INSERT INTO users(id) VALUES("'.$user_id.'")');            
        }
    } else if($username != null) {
            $db->query('UPDATE users SET username="'.($db->escapeString($username)).'" WHERE id="'.$user_id.'"');
    }
    
    if($game != null && $in_game_name != null) {
        $db->query('DELETE FROM user_games WHERE user_id="'.$user_id.'"');
        $db->query('INSERT INTO user_games(user_id, game_id, in_game_name) VALUES("'.$user_id.'", "'.$game.'", "'.$in_game_name.'")');
    }
    
    require('get_profile.php');
    get_profile($db, $user_id);
}