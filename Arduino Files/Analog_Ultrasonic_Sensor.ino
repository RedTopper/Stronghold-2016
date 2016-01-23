/*
3 Pin Ping Sensor
+5 to 5V
GND to GND
AN to ANALOG0

Exports distance from analog ping sensor to serial monitor
Will be turned into Java
*/

int sensorPin = A0;
float sensorValue;
float distance;

void setup () {
  Serial.begin(9600);
}

void loop() {
  sensorValue = analogRead(sensorPin);
  distance = sensorValue * 0.57;
  Serial.println(sensorValue);
  Serial.print(distance);
  Serial.println(" in");
  delay(1000);
}
