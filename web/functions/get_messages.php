<?php
function get_messages($db, $user_id, $other_user_id, $before) {
    $result = $db->query('SELECT * FROM messages WHERE ((from_id="'.$user_id.'" AND to_id="'.$other_user_id.'") OR (to_id="'.$user_id.'" AND from_id="'.$other_user_id.'"))'.($before == null ? '' : ' AND date < "'.$before.'"').' ORDER BY date DESC');
    $current_user = $db->queryRow('SELECT id, username FROM users WHERE id="'.$user_id.'"');
    $other_user = $db->queryRow('SELECT id, username FROM users WHERE id="'.$other_user_id.'"');
    $id_map[$current_user['id']] = $current_user['username'];
    $id_map[$other_user['id']] = $other_user['username'];
    $messages = array();
    while($row = $result->fetch_assoc()) {
        $row['from_username'] = $id_map[$row['from_id']];
        $row['to_username'] = $id_map[$row['to_id']];
        $messages[] = $row;
    }

    outputResponse($messages);
}
?>