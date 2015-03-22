<?php
function get_messages($db, $user_id, $other_user_id, $before) {
    $result = $db->query('SELECT * FROM messages WHERE ((from_id="'.$user_id.'" AND to_id="'.$other_user_id.'") OR (to_id="'.$user_id.'" AND from_id="'.$other_user_id.'"))'.($before == null ? '' : ' AND date < "'.$before.'"').' ORDER BY date DESC');
    
    $messages = array();
    while($row = $result->fetch_assoc()) {
        $messages[] = $row;
    }
    $response = $messages
	
    outputResponse($response);
}
?>