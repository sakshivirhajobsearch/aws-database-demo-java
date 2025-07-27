package aws.database.demo;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

public class DynamoDBExample {

	public static void main(String[] args) {
		
		DynamoDbClient client = DynamoDbClient.builder().build();

		String tableName = "Employees";

		// Put an item
		Map<String, AttributeValue> item = new HashMap<>();
		item.put("id", AttributeValue.builder().n("101").build());
		item.put("name", AttributeValue.builder().s("John Doe").build());

		PutItemRequest putReq = PutItemRequest.builder().tableName(tableName).item(item).build();

		client.putItem(putReq);
		System.out.println("Item inserted.");

		// Get the item
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("id", AttributeValue.builder().n("101").build());

		GetItemRequest getReq = GetItemRequest.builder().tableName(tableName).key(key).build();

		Map<String, AttributeValue> returnedItem = client.getItem(getReq).item();
		if (returnedItem != null) {
			System.out.println("Retrieved item: " + returnedItem);
		} else {
			System.out.println("Item not found");
		}

		client.close();
	}
}