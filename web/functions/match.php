<?php
function match($db, $user_id, $use_location, $use_games) {
    $result = $db->query('SELECT * FROM users WHERE id != "'.$user_id.'"');
    $users = array();
    while($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

     $response = array(
        'matches' => $users
     );
    outputResponse($response, 500);
}
?>