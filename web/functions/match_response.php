<?php
function match_response($db, $gcpm, $user_id, $other_user_id, $matched) {
    if($user_id == $other_user_id) {
        outputError('Can\'t match with yourself', 400);
    }
    
    $updated = false;
    $result = $db->query('SELECT matched FROM matches WHERE user_id="'.$user_id.'" AND other_user_id="'.$other_user_id.'"');
    $exists = $result->num_rows == 1;
    if($exists) {
        $row = $result->fetch_assoc();
        $updated = $row['matched'] != $matched;
        $db->query('UPDATE matches SET matched="'.$matched.'", date='.time().' WHERE user_id="'.$user_id.'" AND other_user_id="'.$other_user_id.'"');
    } else {
        $db->query('INSERT INTO matches(user_id, other_user_id, matched, date) VALUES("'.$user_id.'", "'.$other_user_id.'", "'.$matched.'", '.time().')');
        $updated = true;
    }
    
    $other_matched = false;
    if($matched) {
        $result = $db->query('SELECT matched FROM matches WHERE user_id="'.$other_user_id.'" AND other_user_id="'.$user_id.'"');
        if($result->num_rows == 1) {
            $row = $result->fetch_assoc();
            $other_matched = $row['matched'];
            if($matched && $other_matched && $updated) {
                $username = $db->queryResult('SELECT username FROM users WHERE id="'.$user_id.'"');
                $other_username = $db->queryResult('SELECT username FROM users WHERE id="'.$other_user_id.'"');
                $db->query('INSERT INTO messages(from_id, to_id, date, text) VALUES("system", "'.$other_user_id.'", "'.time().'", "'.$username.' matched with you!")');
                $db->query('INSERT INTO messages(from_id, to_id, date, text) VALUES("system", "'.$user_id.'", "'.time().'", "You matched with '.$other_username.'!")');
            }
        }
    }
    
    outputResponse($matched && $other_matched);
}
?>
