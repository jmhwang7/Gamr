<?php
function update_location($db, $user_id, $lat, $lon) {
    $db->query('UPDATE users
    			SET lat=' .$lat. ', lon=' .$lon. '
    			WHERE id="' .$user_id. '";');
    outputResponse(array('user_id' => $db->insertId()));
}
?>