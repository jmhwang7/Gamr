<?php
// Functions for handling user input.

// Gets a request parameter
function param($name) {
    return $_REQUEST[$name];
}

// Returns true if the request parameter exists and is not empty
function paramIsSet($name) {
    return isset($_REQUEST[$name]) && $_REQUEST[$name] !== '';
}

// Validates a parameter's data type
function validateDataType($value, $type, $typeDetails) {
    switch($type) {
        case 'uuid':
            // UUIDs must be all lowercase with dashes included
            $result = preg_match('/^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/', $value);
            if($result === 1) {
                return true;
            } else if($result === 0) {
                return false;
            } else { // preg_match returns false on error
                outputError('Error validating UUID', 500);
            }
        case 'boolean':
            return $value === 'true' || $value === 'false';
        case 'string':
            return $value !== '';
        case 'int':
            return is_numeric($value) && intval($value) == $value;
        case 'decimal':
            return is_numeric($value);
    }
}
?>