<?php
require_once('classes/sql.php');
$sql = new sql();

//get uid
$uid = $_GET['uid'];

if($uid){
	$sql -> viewHistory($uid);
} else {
	echo "Please pass in a valid uid parameter for the user whose history you'd like to view.";	
}