package tp;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Publisher {

	public static void main(String[] args) {
		String brokerAddress = "broker.hivemq.com";
        int brokerPort = 1883;
        String topic = "Temp";

        String clientId = "JavaPublisher";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient mqttClient = new MqttClient("tcp://" + brokerAddress + ":" + brokerPort, clientId, persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + brokerAddress);
            mqttClient.connect(connOpts);
            System.out.println("Connected to broker");

            while (true) {
                try {
                    // Générer une valeur de température aléatoire entre 20 et 40 degrés Celsius
                	double temperature = Math.round((Math.random() * (40 - 20) + 20) * 100.0) / 100.0;

                    // Publier la valeur de température sur le topic
                    MqttMessage message = new MqttMessage(String.valueOf(temperature).getBytes());
                    mqttClient.publish(topic, message);

                    System.out.println("Temperature published: " + temperature + " °C");

                    // Attendre 10 secondes avant de publier une nouvelle valeur
                    Thread.sleep(10000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }
        

	}

}
