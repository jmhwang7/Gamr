<?php
function get_messages($db, $gcpm, $user_id, $other_user_id, $before) {
    $id_map = array();
    if($other_user_id != null) {
        $result = $db->query('SELECT * FROM messages WHERE ((from_id="'.$user_id.'" AND to_id="'.$other_user_id.'") OR (to_id="'.$user_id.'" AND from_id="'.$other_user_id.'"))'.($before == null ? '' : ' AND date < "'.$before.'"').' ORDER BY date DESC');
    } else {
        $result = $db->query('SELECT * FROM (SELECT * FROM messages WHERE to_id="'.$user_id.'"'.($before == null ? '' : ' AND date < "'.$before.'"').' ORDER BY date DESC) AS t GROUP BY from_id');
    }
    $messages = array();
    while($row = $result->fetch_assoc()) {
        if(!isset($id_map[$row['from_id']])) {
            if($row['from_id'] != 'system')
                $id_map[$row['from_id']] = $db->queryResult('SELECT username FROM users WHERE id="'.$row['from_id'].'"');
            else
                $id_map[$row['from_id']] = '';
        }
        if(!isset($id_map[$row['to_id']])) {
            $id_map[$row['to_id']] = $db->queryResult('SELECT username FROM users WHERE id="'.$row['to_id'].'"');
        }
        $row['from_username'] = $id_map[$row['from_id']];
        $row['to_username'] = $id_map[$row['to_id']];
        $messages[] = $row;
    }

    outputResponse($messages);
}
?>
