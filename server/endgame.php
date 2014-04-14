<?php
echo '<?xml version="1.0" encoding="UTF-8" ?>';
require("../../cse476/db.inc");

$pdo = pdo_connect();

$query = "TRUNCATE Table game_details";
$pdo->query($query);
$query = "TRUNCATE Table moves";
$pdo->query($query);

echo '<endgame status="yes"/>';

?>
