<?php
require_once('classes/reviews.php');
$reviews = new reviews();

//get mode
$mode = $_GET['mode'];

//provider id needed regardless
$pid = $_GET['pid'];

//if we need to insert 
$uid = $_GET['uid'];
$rating = $_GET['rating'];
$review = $_GET['review'];
$friendliness = $_GET['friendliness'];
$communication = $_GET['communication'];
$office_environ = $_GET['office_environment'];


//execute certain functions depending on the mode
if($mode == "insert"){
	$reviews -> insertReview($pid, $uid, $rating, $review, $friendliness, $communication, $office_environ);
} else if($mode == "view"){
	$reviews -> viewReviews($pid);
} else {
	echo "Please pass in either 'insert' or 'view' as a mode parameter.";	
}