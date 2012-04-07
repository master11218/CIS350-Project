<?php

require_once 'includes/constants.php';

class sql{
	
	private $connection;
	
	function __construct(){
	  $this ->connection = new mysqli(HOST, DB_USER, DB_PASSWORD, DB_NAME) or die("There was a problem connecting to the database");
	  mysql_connect(HOST, DB_USER, DB_PASSWORD)or die("cannot connect.. Error: (" . mysql_errno() . ") " . mysql_error()); 
	  mysql_select_db(DB_NAME)or die("cannot select DB.. Error: (" . mysql_errno() . ") " . mysql_error());
	}
	
	function doSearch($name, $hasParking, $acceptingNew, $type, $handicap, $appointments, $creditCard, $lat, $long, $distance){
		$query = "SELECT * FROM `providers`";
		$flag = 1;
		if(!empty($name)){
			if($flag == 1){
				$flag = 2;
				$query = $query." WHERE ";
			} else {
				$query = $query." AND ";
			}
			$query = $query."name = '$name' ";
		}
		if(!empty($hasParking)){
			if($flag == 1){
				$flag = 2;
				$query = $query." WHERE ";
			} else {
				$query = $query." AND ";
			}
			$query = $query."has_parking = '$hasParking' ";
		}
		if(!empty($acceptingNew)){
			if($flag == 1){
				$flag = 2;
				$query = $query." WHERE ";
			} else {
				$query = $query." AND ";
			}
			$query = $query."accepting_new = '$acceptingNew' ";
		}
		if(!empty($type)){
			if($flag == 1){
				$flag = 2;
				$query = $query." WHERE ";
			} else {
				$query = $query." AND ";
			}
			$query = $query."type = '$type' ";
		}
		if(!empty($handicap)){
			if($flag == 1){
				$flag = 2;
				$query = $query." WHERE ";
			} else {
				$query = $query." AND ";
			}
			$query = $query."handicap_access = '$handicap' ";
		}
		if(!empty($appointments)){
			if($flag == 1){
				$flag = 2;
				$query = $query." WHERE ";
			} else {
				$query = $query." AND ";
			}
			$query = $query."appointment_only = '$appointments' ";
		}
		if(!empty($creditCard)){
			if($flag == 1){
				$flag = 2;
				$query = $query." WHERE ";
			} else {
				$query = $query." AND ";
			}
			$query = $query."credit_cards = '$creditCard' ";
		}
		
		//echo $query;
		$result = mysql_query($query);
		$arr = array();
		$found = "no";
		while($row = mysql_fetch_array($result)){
			if($lat != 0 && $long != 0 && $distance != 0){
				if($this->distanceLatLongPair($lat, $long, $row['lat'], $row['long']) < $distance){
					$found = "yes";
					$temp = array();
					$temp['pid'] = $row['pid'];
					$temp['name'] = $row['name'];
					$temp['address'] = $row['address'];
					$temp['city'] = $row['city'];
					$temp['state'] = $row['state'];
					$temp['zip'] = $row['zip'];
					$temp['phone'] = $row['phone'];
					$temp['accepting_new'] = $row['accepting_new'];
					$temp['has_parking'] = $row['has_parking'];
					$temp['type'] = $row['type'];
					$temp['latitude'] = $row['latitude'];
					$temp['longitude'] = $row['longitude'];
					$temp['credit_cards'] = $row['credit_cards'];
					$temp['handicap_access'] = $row['handicap_access'];
					$temp['appointment_only'] = $row['appointment_only'];
					$temp['website'] = $row['website'];
					$temp['hours'] = $row['hours'];
					$temp['average_rating'] = $this->getAvgRating($row['pid']);
					$arr[] = $temp;
				}
			} else {
				$found = "yes";
				$temp = array();
				$temp['pid'] = $row['pid'];
				$temp['name'] = $row['name'];
				$temp['address'] = $row['address'];
				$temp['city'] = $row['city'];
				$temp['state'] = $row['state'];
				$temp['zip'] = $row['zip'];
				$temp['phone'] = $row['phone'];
				$temp['accepting_new'] = $row['accepting_new'];
				$temp['has_parking'] = $row['has_parking'];
				$temp['type'] = $row['type'];
				$temp['latitude'] = $row['latitude'];
				$temp['longitude'] = $row['longitude'];
				$temp['credit_cards'] = $row['credit_cards'];
				$temp['handicap_access'] = $row['handicap_access'];
				$temp['appointment_only'] = $row['appointment_only'];
				$temp['website'] = $row['website'];
				$temp['hours'] = $row['hours'];
				$temp['average_rating'] = $this->getAvgRating($row['pid']);
				$arr[] = $temp;
			}
		}
		if($found == "yes"){
			$result = array();
			$result["providers"] = $arr;
			echo json_encode($result);
		} else 
			echo "No results found";
	}
	
	function getProviderInfo($pid){
		$query = "SELECT * FROM `providers` WHERE `pid` = '$pid'";

		$result = mysql_query($query);
		if($row = mysql_fetch_array($result)){
				$temp = array();
				$temp['pid'] = $row['pid'];
				$temp['name'] = $row['name'];
				$temp['address'] = $row['address'];
				$temp['city'] = $row['city'];
				$temp['state'] = $row['state'];
				$temp['zip'] = $row['zip'];
				$temp['phone'] = $row['phone'];
				$temp['accepting_new'] = $row['accepting_new'];
				$temp['has_parking'] = $row['has_parking'];
				$temp['type'] = $row['type'];
				$temp['latitude'] = $row['latitude'];
				$temp['longitude'] = $row['longitude'];
				$temp['credit_cards'] = $row['credit_cards'];
				$temp['handicap_access'] = $row['handicap_access'];
				$temp['appointment_only'] = $row['appointment_only'];
				$temp['website'] = $row['website'];
				$temp['hours'] = $row['hours'];
				$temp['average_rating'] = $this->getAvgRating($row['pid']);
				
				$return = array();
				$return["provider"] = $temp;
				echo json_encode($return);
		} else {
			echo "Invalid PID";
		}
	}
	
	function getAvgRating($pid){
		$query = "SELECT * FROM `ratings` WHERE `pid` = $pid";
		$result = mysql_query($query);
		
		$total = 0;
		$count = 0;
		while($row = mysql_fetch_array($result)){
			$total += $row['rating'];
			$count++;
		}
		
		if($count == 0){
			return 0;
		} else {
			return $total/$count;
		}
	}
	
	function viewHistory($uid){
		if($uid == 0){
			echo "Please specify a valid user ID";
			exit(0);
		}
		
		$query = "SELECT * FROM `ratings` WHERE `uid` = $uid";
		$result = mysql_query($query);
		
		$arr = array();
		while($row = mysql_fetch_array($result)){
			$temp = array();
			$temp['uid'] = $row['uid'];
			$temp['pid'] = $row['pid'];
			$temp['name'] = $this -> getName($row['uid']);
			$temp['provider_name'] = $this ->getProviderName($row['pid']);
			$temp['rating'] = $row['rating'];
			$temp['time'] = $row['time'];
			$temp['review'] = $row['review'];
			$arr[] = $temp;
		} 
		
		$result = array();
		$result["reviews"] = $arr;
		echo json_encode($result);
	}
	
	function viewReviews($pid){
		if($pid == 0){
			echo "Please specify a valid provider ID";
			exit(0);
		}
		
		$query = "SELECT * FROM `ratings` WHERE `pid` = $pid";
		$result = mysql_query($query);
		
		$arr = array();
		while($row = mysql_fetch_array($result)){
			$temp = array();
			$temp['uid'] = $row['uid'];
			$temp['pid'] = $row['pid'];
			$temp['name'] = $this -> getName($row['uid']);
			$temp['provider_name'] = $this ->getProviderName($row['pid']);
			$temp['rating'] = $row['rating'];
			$temp['time'] = $row['time'];
			$temp['review'] = $row['review'];
			$arr[] = $temp;
		} 
		
		$result = array();
		$result["reviews"] = $arr;
		echo json_encode($result);
	}
	
	function getProviderName($pid){
		$query = "SELECT * FROM `providers` WHERE `pid` = $pid";
		$result = mysql_query($query);
		$row = mysql_fetch_array($result);
		return $row['name'];	
	}
	
	function getName($uid){
		$query = "SELECT * FROM `users` WHERE `uid` = $uid";
		$result = mysql_query($query);
		$row = mysql_fetch_array($result);
		return $row['name'];
	}
	
	function registerUser($name, $address, $email, $gender, $phone){
		$query = "INSERT INTO `users` (`name`, `address`, `email`, `gender`, `phone`) VALUES ('$name', '$address', '$email', '$gender', '$phone')";
		$result = mysql_query($query);
		echo mysql_insert_id();
	}
	
	function insertReview($pid, $uid, $rating, $review){
		$review = addslashes($review);
		$query = "INSERT INTO `ratings` (`uid`, `pid`, `rating`, `review`) VALUES ('$uid', '$pid', '$rating', '$review')";
		$result = mysql_query($query);
		echo "Success";
	}
	
	function distanceLatLongPair($lat1, $long1, $lat2, $long2){
   		$pi80   = M_PI / 180;
	  	$lat1  *= $pi80;
      	$long1 *= $pi80;
    	$lat2  *= $pi80;
    	$long2 *= $pi80;
   
	  	$r = 6372.797; // mean radius of Earth in km
    	$dlat = $lat2 - $lat1;
    	$dlong = $long2 - $long1;
   	  	$a = sin($dlat / 2) * sin($dlat / 2) + cos($lat1) * cos($lat2) * sin($dlong / 2) * sin($dlong / 2);
    	$c = 2 * atan2(sqrt($a), sqrt(1 - $a));
    	$km = $r * $c;
   
	  	return $km * 0.621371192;
	}
}