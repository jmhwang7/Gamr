<?php
function errorHandler($errno, $errstr, $errfile, $errline) {
    $errorMessage = 'Internal error('.$errfile.':'.$errline.'): '.$errstr;
    file_put_contents('error.log', $errorMessage."\n", FILE_APPEND);
    if(DEBUG) {
        outputError($errorMessage, 500);
    } else {
        outputError('A server error occured.');
    }
    return true;
}
set_error_handler('errorHandler');
?>