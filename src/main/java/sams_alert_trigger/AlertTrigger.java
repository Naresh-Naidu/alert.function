package sams_alert_trigger;

import com.microsoft.azure.functions.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Cosmos DB trigger.
 */
public class AlertTrigger {
    /**
     * This function will be invoked when there are inserts or updates in the specified database and collection.
     */
    @FunctionName("AlertTrigger")
    public void run(
        @CosmosDBTrigger(
            name = "items",
            databaseName = "alert",
            collectionName = "items",
            leaseCollectionName="lease",
            connectionStringSetting = "DB_CONNECTION",
            createLeaseCollectionIfNotExists = true
        )
        Object[] items,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Cosmos DB trigger function executed.");
        context.getLogger().info("Documents count: " + items.length);
        context.getLogger().info("record has been changed: "+items[0]);
        
        try {
        	context.getLogger().info("connecting to alert service");
    		URL url = new URL("http://localhost:9090/getAlert/");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("POST");
    		
    		String content="";
    		conn.setDoOutput(true);
    		OutputStream os=conn.getOutputStream();
    		os.write(content.getBytes());
    		os.flush();
    		os.close();
    		//conn.setRequestProperty("Accept", "application/json");
    		context.getLogger().info("connection completed..");
    		
        } catch (MalformedURLException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  }
    }
}
