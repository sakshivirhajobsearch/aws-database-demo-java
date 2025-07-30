package aws.database.demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DynamoDBExampleTest {

	private static DynamoDbClient client;
	private static final String TABLE_NAME = "Employees";
	private static final String TEST_ID = "999";
	private static final String TEST_NAME = "Test User";

	@BeforeAll
	static void setup() {
		client = DynamoDbClient.builder().build();
	}

	@Test
	void testPutAndGetItem() {
		// Insert item
		Map<String, AttributeValue> item = new HashMap<>();
		item.put("id", AttributeValue.builder().n(TEST_ID).build());
		item.put("name", AttributeValue.builder().s(TEST_NAME).build());

		PutItemRequest putReq = PutItemRequest.builder().tableName(TABLE_NAME).item(item).build();

		client.putItem(putReq);

		// Retrieve item
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("id", AttributeValue.builder().n(TEST_ID).build());

		GetItemRequest getReq = GetItemRequest.builder().tableName(TABLE_NAME).key(key).build();

		Map<String, AttributeValue> returnedItem = client.getItem(getReq).item();
		assertNotNull(returnedItem);
		assertEquals(TEST_NAME, returnedItem.get("name").s());
	}

	@AfterAll
	static void tearDown() {
		// Clean up test item
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("id", AttributeValue.builder().n(TEST_ID).build());

		DeleteItemRequest delReq = DeleteItemRequest.builder().tableName(TABLE_NAME).key(key).build();

		client.deleteItem(delReq);
		client.close();
	}
}
