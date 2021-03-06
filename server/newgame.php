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
    $userid = getUser($pdo, $user, $password);
    if( $userid == "fail") {
        echo '<newgame status="no" msg="login error" />';
        exit;
    }

    $query = "INSERT into game_details values(null,$userid, 0, 0, 0)";
    $pdo->query($query);

    echo '<newgame status="yes"/>';
    exit;
}

/**
 * Ask the database for the user ID. If the user exists, the password
 * must match.
 * @param $pdo PHP Data Object
 * @param $user The user name
 * @param $password Password
 * @return id if successful or exits if not
 */
function getUser($pdo, $user, $password) {
    $userQ = $pdo->quote($user);
    $query = "SELECT uid, password from users where username=$userQ";
    $rows = $pdo->query($query);
    if($row = $rows->fetch()) {
        // We found the record in the database
        // Check the password
        if($row['password'] != $password) {
            echo $password;
            return "fail";
        }

        return $row['uid'];
    }

    return "fail";
}

?>
