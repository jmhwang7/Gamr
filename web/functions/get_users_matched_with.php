<?php
function get_users_matched_with($db, $gcpm, $user_id) {
	$results = $db->query('SELECT user_id as id, username FROM matches JOIN users ON matches.user_id = users.id WHERE other_user_id = "'.$user_id.'" AND matched = 1');
	$matches = array();
	while($row = $results->fetch_assoc()) {
		$matches[] = $row['id'];
	}

	$results = $db->query('SELECT other_user_id as id, username FROM matches JOIN users ON matches.other_user_id = users.id WHERE user_id = "'.$user_id.'" AND matched = 1');
	$other_matches = array();
	while($row = $results->fetch_assoc()) {
		$other_matches[] = array(
			'id' => $row['id'],
			'username' => $row['username']
		);
	}
	
	$matches = array_intersect($matches, $other_matches);
	
	outputResponse($matches);
}
?>