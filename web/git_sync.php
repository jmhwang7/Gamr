<?php
header('Content-type: text/plain');
echo shell_exec('git checkout . 2>&1');
echo shell_exec('git pull 2>&1');
?>
