<?php
function update_profile($db, $user_id, $username, $games, $in_game_names) {
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
    
    if($games != null && $in_game_names != null) {
        $db->query('DELETE FROM user_games WHERE user_id="'.$user_id.'"');
        for($i = 0; $i < count($games); $i++) {
            $db->query('INSERT INTO user_games(user_id, game_id, in_game_name) VALUES("'.$user_id.'", "'.$games[$i].'", "'.$in_game_names[$i].'")');
        }
    }
    
    require('get_profile.php');
    get_profile($db, $user_id);
}