<?php 

	$conn = mysqli_connect('localhost','id12123509_mirzashasan','123hasan456','id12123509_mirzashasan95');
	$result = array();
	$result['Devices'] = array();
	$select= "SELECT * from Devices ";
	$responce = mysqli_query($conn,$select);
	
	while($row = mysqli_fetch_array($responce))
		{
			$index['device_id']  = $row['1'];
			$index['device_name']    = $row['2'];
		
			
			array_push($result['Devices'], $index);
		}
			
			$result["success"]="1";
			echo json_encode($result);
			mysqli_close($conn);
 ?>