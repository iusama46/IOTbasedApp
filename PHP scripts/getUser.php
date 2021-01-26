<?php 

	$conn = mysqli_connect('localhost','id12123509_mirzashasan','123hasan456','id12123509_mirzashasan95');
	$checkDeviceID= $_POST['device_id'];
	$result = array();
	$result['App_Users'] = array();
	$select= "SELECT * from App_Users where device_id='$checkDeviceID'";
	$responce = mysqli_query($conn,$select);
	
	while($row = mysqli_fetch_array($responce))
		{
			//$index['id']  = $row['0'];
			$index['name']  = $row['1'];
			$index['email']  = $row['3'];
			$index['phone']  = $row['4'];
			$index['city']  = $row['5'];
			array_push($result['App_Users'], $index);
		}
			
			$result["success"]="1";
			echo json_encode($result);
			mysqli_close($conn);

 ?>