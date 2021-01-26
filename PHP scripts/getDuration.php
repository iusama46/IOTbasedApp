<?php 

	$conn = mysqli_connect('localhost','id12123509_mirzashasan','123hasan456','id12123509_mirzashasan95');
	$checkDeviceID= $_POST['device_id'];
	$result = array();
	$result['Usage_History'] = array();
	$select= "SELECT * from Usage_History where device_id='$checkDeviceID'";
	$responce = mysqli_query($conn,$select);
	
	while($row = mysqli_fetch_array($responce))
		{
			$index['session_id']  = $row['0'];
			$index['duration']    = $row['4'];
		
			
			array_push($result['Usage_History'], $index);
		}
			
			$result["success"]="1";
			echo json_encode($result);
			mysqli_close($conn);

 ?>