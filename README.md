# pametna-kuca
Mobilna aplikacija za praćenje senzora i pokretanje aktuatora pametne kuće.

Još dodatno:

Cron za pokretanje python skripte svakih 10 minuta:
*/10 * * * *  /home/pi/Desktop/writeToDB.py

Python skripta za pisanje u bazu:
#!/usr/bin/python
import MySQLdb
import time
from datetime import datetime
from sense_hat import SenseHat
db = MySQLdb.connect(host="localhost", user="root", passwd="raspberry", db="Senzori")
cur = db.cursor()
now = datetime.now()
now = now.strftime('%Y-%m-%d %H:%M:%S')
sense = SenseHat()
temp = "%.2g" % sense.temperature
pressure = "%.4g" % sense.pressure
try:
   cur.execute("""INSERT INTO Senzori VALUES (%s,%s,%s)""",(now,temp,pressure))
   db.commit()
except:
   db.rollback()
db.close()

PHP kod koji čita iz baze i radi JSON encode:
<?php
 require_once('dbConnect.php');
 $sql = "SELECT * FROM Senzori";
 $r = mysqli_query($con,$sql);
 while($res = mysqli_fetch_array($r)){
   $result = array();
   array_push($result,array(
     "Datum"=>$res['Datum'],
     "Temperatura"=>$res['Temperatura'],
     "Pritisak"=>$res['Pritisak']));
   }
   echo json_encode(array("result"=>$result));
   mysqli_close($con);
 }
?>
