<?php
echo '<?xml version="1.0" encoding="UTF-8" ?>';
require("../../cse476/db.inc");

processXml($_GET['user'], $_GET['pw']);

/**
 * Process the XML query
 * @param $xmltext the provided XML
 */
function processXml($user, $password) {
    $pdo = pdo_connect();
    $userid = createUser($pdo, $user, $password);
    echo '<create status="yes" uid="' . $userid . '" />';
    exit;
}


function createUser($pdo, $user, $password) {
    $userQ = $pdo->quote($user);
    $query = "SELECT uid from users where username=$userQ";
    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        echo '<create status="no" msg="user already exists" />';
        exit;
    }

    $queryInsert = "INSERT INTO users VALUES (NULL,'". $user . "','" . $password . "')";
    $pdo->query($queryInsert);
    echo '<create status="yes" />';
    exit;
}

?>
