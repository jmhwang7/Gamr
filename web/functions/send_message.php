<?php
function send_message($db, $user_id, $other_user_id, $text) {
    $eText = $db->escapeString($text);
    $db->query('INSERT INTO messages(
        from_id, to_id, date, text
    ) VALUES(
        "'.$user_id.'", "'.$other_user_id.'", '.time().', "'.$eText.'"
    )');
    outputResponse(array('message_id' => $db->insertId()));
}
?>