<?php
function update_location($db, $gcpm, $user_id, $lat, $lon) {
    $userExists = $db->query('SELECT id FROM users WHERE id="'.$user_id.'"')->num_rows == 1;
    if($userExists) {
        $db->query('UPDATE users
    			SET lat=' .$lat. ', lon=' .$lon. '
    			WHERE id="' .$user_id. '";');        
    } else {
        $db->query('INSERT INTO users(id, lat, lon) VALUES("'.$user_id.'", "'.$lat.'", "'.$lon.'")');
    }


    outputResponse(array('affected_rows' => $db->affectedRows()));
}
?>