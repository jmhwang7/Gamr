<?php
function update_profile($db, $gcpm, $user_id, $username, $game, $in_game_name) {
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
        // Update LoL rank from API if needed
        if($game == GAME_LOL) {
            require('update_game_field.php');
            global $LOL_RANKS_RLT;
            // TODO use the server that the user plays on instead of NA
            $rankString = getRank('na', $in_game_name);
            if(isset($LOL_RANKS_RLT[$rankString])) {
                $rank = $LOL_RANKS_RLT[$rankString];
                update_game_field($db, $user_id, GAME_LOL, FIELD_LOL_RANK, $rank);
            }
        }
    }
    
    require('get_profile.php');
    get_profile($db, $user_id);
}