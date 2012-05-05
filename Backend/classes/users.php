<?php
//login details are stored here
require_once 'includes/constants.php';

class users{
	
	//connect to the database once and save the connection
	private $connection;
	
	//connect to the database once in the constructor
	function __construct(){
	  $this ->connection = new mysqli(HOST, DB_USER, DB_PASSWORD, DB_NAME) or die("There was a problem connecting to the database");
	  mysql_connect(HOST, DB_USER, DB_PASSWORD)or die("cannot connect.. Error: (" . mysql_errno() . ") " . mysql_error()); 
	  mysql_select_db(DB_NAME)or die("cannot select DB.. Error: (" . mysql_errno() . ") " . mysql_error());
	}
	
	//method that creates the user
	function registerUser($name, $address, $email, $gender, $phone){
		//prevent against mysql injection
		$name = addslashes($name);
		$address = addslashes($address);
		$email = addslashes($email);
		
		//db interactions
		$query = "INSERT INTO `users` (`name`, `address`, `email`, `gender`, `phone`) VALUES ('$name', '$address', '$email', '$gender', '$phone')";
		$result = mysql_query($query);
		echo mysql_insert_id();
	}
	
	//helper method to get the provider's name
	function getProviderName($pid){
		$query = "SELECT * FROM `providers` WHERE `pid` = $pid";
		$result = mysql_query($query);
		$row = mysql_fetch_array($result);
		return $row['name'];	
	}
	
	//get the user's rating history
	function viewHistory($uid){
		//check for invalid input
		if($uid < 0){
			echo "Please specify a valid user ID";
			exit(0);
		}
		
		//do the query
		$query = "SELECT * FROM `ratings` WHERE `uid` = $uid";
		$result = mysql_query($query);
		
		//get array ready for json
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
			$arr[] = $temp;
		} 
		
		//output json
		$result = array();
		$result["reviews"] = $arr;
		echo json_encode($result);
	}
	
	//helper method to get user's names
	function getName($uid){
		$query = "SELECT * FROM `users` WHERE `uid` = $uid";
		$result = mysql_query($query);
		$row = mysql_fetch_array($result);
		return $row['name'];
	}
}