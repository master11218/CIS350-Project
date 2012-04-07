<?php
require_once('classes/sql.php');
$sql = new sql();

//get mode
$mode = $_GET['mode'];

//provider id needed regardless
$pid = $_GET['pid'];

//if we need to insert 
$uid = $_GET['uid'];
$rating = $_GET['rating'];
$review = $_GET['review'];




if($mode == "insert"){
	$sql -> insertReview($pid, $uid, $rating, $review);
} else if($mode == "view"){
	$sql -> viewReviews($pid);
} else {
	echo "Please pass in either 'insert' or 'view' as a mode parameter.";	
}