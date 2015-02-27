<?php
class DBMySQLi {
    private $this;
    function __construct() {
        $this->link = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
        if($this->link->connect_errno) {
            output_error('Could not connect to the database', 500);
        }
    }
    
    function escapeString($str) {
        return $this->link->escape_string($str);
    }
    
    function query($query) {
        $result = $this->link->query($query);
        if($this->link->errno) {
            outputError('Database errorL '.$this->link->error);
        }
        return $result;
    }
    
    // Shortcut to query one row from the DB as an associative array
    function queryRow($query) {
        $result = $this->query($query);
        if($result->num_rows == 0) {
            return null;
        }
        return $result->fetch_assoc();
    }
}
$db = new DBMySQLi();
?>