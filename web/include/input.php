<?php
// Functions for handling user input.

// Gets a request parameter
function param($name) {
    return $_REQUEST[$name];
}

// Returns true if the request parameter exists and is not empty
function paramIsSet($name) {
    return isset($_REQUEST[$name]) && !empty($_REQUEST[$name]);
}
?>