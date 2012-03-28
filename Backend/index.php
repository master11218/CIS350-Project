<?php
require_once('classes/sql.php');
$sql = new sql();

//name of the provider
$name = $_GET['name'];

//filters
$hasParking = $_GET['has_parking'];
$acceptingNew = $_GET['accepting_new'];
$type = $_GET['type'];
$handicap = $_GET['handicap'];
$appointments = $_GET['appointment_only'];
$creditCard = $_GET['credit_card'];

//location
$lat = $_GET['lat'];
$long = $_GET['long'];
$distance = $_GET['distance'];

//info on a specific provider
$pid = $_GET['pid'];


//get provider info on a single provider
if($pid){
	$sql->getProviderInfo($pid);
} else {
	//do a general search
	$sql ->doSearch($name, $hasParking, $acceptingNew, $type, $handicap, $appointments, $creditCard, $lat, $long, $distance);
}