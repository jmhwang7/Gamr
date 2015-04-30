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
                $user = $db->queryRow('SELECT * FROM users WHERE id="'.$user_id.'"');
                $username = $user['username'];
                $other_user = $db->queryRow('SELECT * FROM users WHERE id="'.$other_user_id.'"');
                $other_username = $other_user['username'];
                $db->query('INSERT INTO messages(from_id, to_id, date, text) VALUES("system", "'.$other_user_id.'", "'.time().'", "You matched with '.$username.'!")');
                $db->query('INSERT INTO messages(from_id, to_id, date, text) VALUES("system", "'.$user_id.'", "'.time().'", "You matched with '.$other_username.'!")');

                $messageType = 'match';
                $gcpm->setDevices($user['registration_id']);
                $response = $gcpm->send($messageType, array('other_user' => $other_username));
                $gcpm->setDevices($other_user['registration_id']);
                $response = $gcpm->send($messageType, array('other_user' => $username));
            }
        }
    }
    
    outputResponse($matched && $other_matched);
}
?>
