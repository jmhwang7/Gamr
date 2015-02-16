<?php
// Main API controller

define('API_VERSION', 1);
require('include/input.php');
require('include/response.php');
require('functions.php');

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

//TODO Validate the function parameters here

require('functions/'.$function.'.php');

//TODO Call the function here
?>