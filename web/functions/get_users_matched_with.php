<?php
function get_users_matched_with($db, $gcpm, $user_id) {
	$results = $db->query('SELECT user_id FROM matches WHERE other_user_id = "'.$user_id.'" AND matched = 1');
	$matches = array();
	while($row = $results->fetch_assoc()) {
		$matches[] = $row['user_id'];
	}

	$results = $db->query('SELECT other_user_id FROM matches WHERE user_id = "'.$user_id.'" AND matched = 1');
	$other_matches = array();
	while($row = $results->fetch_assoc()) {
		$other_matches[] = $row['other_user_id'];
	}
	
	$matches = array_intersect($matches, $other_matches);
	
	outputResponse($matches);
}
?>