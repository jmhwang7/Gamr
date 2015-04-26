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
        case 'user_id':
            return $value == 'system' || ($value != '' && strpos($value, '@') !== FALSE);
        case 'boolean':
            return $value === 'true' || $value === 'false';
        case 'int':
            return is_numeric($value) && intval($value) == $value;
        case 'decimal':
            return is_numeric($value);
        case 'array':
            $array = json_decode($value);
            if($array === false)
                return false;
            else {
                foreach($array as $e) {
                    if(!validateDataType($e, $typeDetails, null))
                        return false;
                }
                return true;
            }
        default:
            return true;
    }
}

// Convert a string parameter into the proper data type
function convertDataType($value, $type, $typeDetails) {
    switch($type) {
        case 'boolean':
            return $value === 'true' ? true : false;
        case 'user_id':
            return trim($value);
        case 'int':
            return intval($value);
        case 'decimal':
            return floatval($value);
        case 'array':
            $array = json_decode($value);
            for($i = 0; $i < count($array); $i++) {
                $array[$i] = convertDataType($array[$i], $typeDetails, null);
            }
            return $array;
        default:
            return $value;
    }
}
?>
