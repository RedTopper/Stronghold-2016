/*
Takes Photoelectric sensor (uses 12V) and uses switch to input to A0 (Analog)
(USE COMMON GROUND)
Will turn into Java
*/
void setup(){
  Serial.begin(9600);
  pinMode (A0, INPUT);
}
void loop(){
  int input = analogRead(A0);
  Serial.println(analogRead(A0));
  delay (1000);
}
