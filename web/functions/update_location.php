<?php
function update_location($db, $user_id, $lat, $lon) {
    $db->query('UPDATE users
    			SET lat=' .$lat. ', lon=' .$lon. '
    			WHERE id="' .$user_id. '";');
    // http_response_code(200);
    outputResponse(array('affected_rows' => $db->affectedRows()));
    // outputResponse(array('user_id' => $db->insertId()));
}
?>