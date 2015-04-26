<?php
function send_message($db, $gcpm, $user_id, $other_user_id, $text) {
    $eText = $db->escapeString($text);
    $db->query('INSERT INTO messages(
        from_id, to_id, date, text
    ) VALUES(
        "'.$user_id.'", "'.$other_user_id.'", '.time().', "'.$eText.'"
    )');
    $user = $db->queryRow('SELECT * FROM users WHERE id="'.$other_user_id.'"');
    $gcpm->setDevices($user['registration_id']);
    $response = $gcpm->send($text);
    outputResponse(array('message_id' => $db->insertId()));
}
?>