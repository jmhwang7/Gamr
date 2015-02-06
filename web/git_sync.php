<?php
header('Content-type: text/plain');
echo shell_exec('git pull 2>&1');
?>
