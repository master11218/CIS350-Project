<?php

require_once 'includes/constants.php';

class reviews{
	
	private $connection;
	
	function __construct(){
	  $this ->connection = new mysqli(HOST, DB_USER, DB_PASSWORD, DB_NAME) or die("There was a problem connecting to the database");
	  mysql_connect(HOST, DB_USER, DB_PASSWORD)or die("cannot connect.. Error: (" . mysql_errno() . ") " . mysql_error()); 
	  mysql_select_db(DB_NAME)or die("cannot select DB.. Error: (" . mysql_errno() . ") " . mysql_error());
	}
	
	//view the reviews for a given provider
	function viewReviews($pid){
		//check for invalid input
		if($pid < 0){
			echo "Please specify a valid provider ID";
			exit(0);
		}
		
		$query = "SELECT * FROM `ratings` WHERE `pid` = $pid";
		$result = mysql_query($query);
		
		//format and output the results
		$arr = array();
		while($row = mysql_fetch_array($result)){
			$temp = array();
			$temp['uid'] = $row['uid'];
			$temp['pid'] = $row['pid'];
			$temp['name'] = $this -> getName($row['uid']);
			$temp['provider_name'] = $this ->getProviderName($row['pid']);
			$temp['rating'] = $row['rating'];
			$temp['rating'] = number_format($temp['rating'], 2);
			$temp['time'] = $row['time'];
			$temp['review'] = $row['review'];
			$temp['communication'] = $row['communication'];
			$temp['friendliness'] = $row['friendliness'];
			$temp['office_environment'] = $row['office_environment'];
			$arr[] = $temp;
		} 
		
		$result = array();
		$result["reviews"] = $arr;
		echo json_encode($result);
	}
	
	//helper method to get the name of a provider
	function getProviderName($pid){
		$query = "SELECT * FROM `providers` WHERE `pid` = $pid";
		$result = mysql_query($query);
		$row = mysql_fetch_array($result);
		return $row['name'];	
	}
	
	//helper method to get the name of a user
	function getName($uid){
		$query = "SELECT * FROM `users` WHERE `uid` = $uid";
		$result = mysql_query($query);
		$row = mysql_fetch_array($result);
		return $row['name'];
	}
	
	//insert a review/rating for a provider
	function insertReview($pid, $uid, $rating, $review, $friendliness, $communication, $office_environ){
		$review = addslashes($review);
		$query = "INSERT INTO `ratings` (`uid`, `pid`, `rating`, `review`, `friendliness`, `communication`, `office_environment`) VALUES ('$uid', '$pid', '$rating', '$review', '$friendliness', '$communication', '$office_environ')";
		$result = mysql_query($query);
		echo "Success";
	}
}