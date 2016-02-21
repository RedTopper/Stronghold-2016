void setup(){
  Serial.begin(9600);
  pinMode(A0,INPUT);
  pinMode(0, INPUT);
}
void loop(){
  boolean output = true;
  double input;
  input = analogRead(A0);
  /*if (input <= 250){
    output = true;
  }
  else {
    output = false;
  }  
  */
  Serial.println(input);
  delay(1000);
}
