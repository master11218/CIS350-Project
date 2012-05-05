<?php
require_once('classes/users.php');
$users = new users();

//get uid
$uid = $_GET['uid'];

//execute function only if there is input
if($uid){
	$users -> viewHistory($uid);
} else {
	echo "Please pass in a valid uid parameter for the user whose history you'd like to view.";	
}