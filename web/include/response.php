<?php
// Functions for generating a response

// Outputs the response as a JSON object and exits.
function outputResponse($responseArray) {
    header('Content-type: text/plain');
    echo json_encode($responseArray);
    exit;
}

// Outputs an error with the specified message and HTTP error code.
function outputError($message, $type=400) {
    $httpErrors = array(
        400 => 'Bad Request',
        500 => 'Internal Server Error'
    );
    header('HTTP/1.x '.$type.' '.$httpErrors[$type]);
    outputResponse(array(
        'error' => $message
    ));
}
?>