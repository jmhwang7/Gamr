<?php
function update_gcm_device_id($db, $gcpm, $user_id, $gcm_device_id) {
    $userExists = $db->query('SELECT id FROM users WHERE id="'.$user_id.'"')->num_rows == 1;
    if($userExists) {
        $db->query('UPDATE users
    			SET registration_id="' .$gcm_device_id. '"
    			WHERE id="' .$user_id. '";');
    }

    outputResponse(array('affected_rows' => $db->affectedRows()));
}
?>