<?php
// Main API controller

define('API_VERSION', 1);
define('DEBUG', true);
require('include/config.php');

require('include/input.php');
require('include/response.php');
require('include/errorHandler.php');
require('include/db.php');
require('include/riotApi.php');
require('include/GCMPushMessage.php');
require('functions.php');

$apiKey = "AIzaSyDYPI0hABUTsSoFUuxZLI3cH2PKc5Ncpuo";
$gcpm = new GCMPushMessage($apiKey);

if(!paramIsSet('version') || !is_numeric(param('version'))) {
    outputError('API version not specified', 400);
} else if(param('version') != API_VERSION) {
    outputError('Unsupported API version', 400);
}

if(!paramIsSet('function')) {
    outputError('API function not specified', 400);
}

$function = param('function');
if(!isset($functions[$function])) {
    outputError('API function not found', 400);
}

// Validate all of the parameters
$functionDef = $functions[$function];
$functionParams = $functionDef['params'];
$callParams = array($db, $gcpm); // An indexed array of parameters that will be passed to the function
foreach($functionParams as $param => $paramDef) {
    if(!paramIsSet($param)) {
        if($paramDef['required']) {
            outputError('Missing required parameter: '.$param, 400);
        } else { // If a non-required parameter is not specified, set it to the default value
            $callParams[] = $paramDef['default'];
            continue;
        }
    }
    
    $value = param($param);
    if(!validateDataType($value, $paramDef['type'], isset($paramDef['typeDetails']) ? $paramDef['typeDetails'] : null)) {
        outputError('Invalid parameter type: '.$param, 400);
    } else {
        $callParams[] = convertDataType($value, $paramDef['type'], $value, isset($paramDef['typeDetails']) ? $paramDef['typeDetails'] : null);
    }
}

// Log request
if(DEBUG) {
    $message = '['.date(DATE_RFC2822).', '.$_SERVER['REMOTE_ADDR'].'] '.$function.' -> '.print_r($_REQUEST, true);
    file_put_contents('request.log', $message."\n", FILE_APPEND);

}

// Call the function
require('functions/'.$function.'.php');
call_user_func_array($function, $callParams);

// The API call function should always output a response and exit, so return an error if we get here
outputError('No data', 500);
?>