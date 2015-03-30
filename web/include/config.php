<?php
require('config.private.php');
define('MAX_DISTANCE', 200);
define('GAME_LOL', 1);
define('FIELD_LOL_ROLE', 1);
define('FIELD_LOL_RANK', 2);
define('FIELD_LOL_GAMEMODE', 3);
define('FIELD_LOL_SERVER', 4);
$LOL_RANKS = array('Unranked', 'Bronze', 'Silver', 'Gold', 'Platinum', 'Diamond', 'Master', 'Challenger');
$LOL_RANKS_RLT = array();
for($i = 0; $i < count($LOL_RANKS); $i++) {
        $LOL_RANKS_RLT[$LOL_RANKS[$i]] = $i;
}
?>