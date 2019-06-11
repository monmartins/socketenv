#include <ESP8266WiFi.h>

WiFiClient client;
int LED = 2;

const uint16_t port = 5000;
const char * host = "192.168.1.105";
int timeout = 1000;
void setup()
{
  Serial.begin(115200);
  Serial.println();

  WiFi.begin("TP-LINK_C45A14", "27C45A14");

  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println();

  Serial.print("Connected, IP address: ");
  Serial.println(WiFi.localIP());
  pinMode(LED, OUTPUT); 
  

    if (!client.connect(host, port)) {

        Serial.println("Connection to host failed");

        delay(1000);
        return;
    }

    Serial.println("Connected to server successful!");

    client.println("new-lamp");     
        digitalWrite(LED, HIGH); 

}

void loop() {

//    while(client.available() == 0)
//    {
//      if(millis() - timeout > 5000)
//      {
//        Serial.println("Timeout to server!");
//        break;
//      }
//    }

    /* Read in the data in the stream */
    while(client.available() > 0)
    {
      String receive = client.readStringUntil('\n');
      if(receive.equals("1")){    
  digitalWrite(LED, LOW);
      }
      if(receive.equals("0")){    
        digitalWrite(LED,HIGH );  
      }
      Serial.println(receive);
    }
 }
