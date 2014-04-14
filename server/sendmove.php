<?php
echo '<?xml version="1.0" encoding="UTF-8" ?>';
require("../../cse476/db.inc");

processXml($_GET['user'], $_GET['pw'], $_GET['number'], $_GET['x'], $_GET['y'], $_GET['weight']);

/**
 * Process the XML query
 * @param $xmltext the provided XML
 */
function processXml($user, $password, $numba, $ex, $why, $wait) {
    $pdo = pdo_connect();
    $userid = getUser($pdo, $user, $password);
    if( $userid == "fail") {
        echo '<game status="no" msg="login error" />';
        exit;
    }

    $playaone = 0;
    $playatwo = 0;
    $onename = "";
    $twoname = "";
    $playaquery = "SELECT playeroneId, playertwoId FROM game_details";
    $rows = $pdo->query($playaquery);
    if($row = $rows->fetch()) {
        $playaone = $row['playeroneId'];
        $playatwo = $row['playertwoId'];
    }

    $playernamequery = "SELECT username FROM users WHERE uid=$playaone";
    $rows = $pdo->query($playernamequery);
    if($row = $rows->fetch()) {
        $onename = $row['username'];
    }
    $playernamequery = "SELECT username FROM users WHERE uid=$playatwo";
    $rows = $pdo->query($playernamequery);
    if($row = $rows->fetch()) {
        $twoname = $row['username'];
    }
    $id = 0;
    if($user == $onename) {
        $id = 0x7f020002;
    }
    else {
        $id = 0x7f020006;
    }
    $query = "INSERT into moves values ($numba, $ex, $why, $id, $wait, \"$user\")";
    $pdo->query($query);
    echo "<move status=yes />";

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
