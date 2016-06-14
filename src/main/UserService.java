package main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import com.cloudera.livy.LivyClient;
import com.cloudera.livy.LivyClientBuilder;

@Path("/UserService")
public class UserService {

	@GET
	@Path("/users")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsers() throws Exception {
		String responseValue = "";
//		SparkConf conf = new SparkConf();
//		JavaSparkContext jsc = new JavaSparkContext();
		
		Double reval = (double) 0;
		LivyClient client = new LivyClientBuilder()
				  .setURI(new URI("http://192.168.1.47:8998"))
				  .build();

				String piJar = "hdfs://192.168.1.170:9000/Pooc21.jar";
//		String piJar = "/home/user1/Pooc18.jar";
				try {
				  System.err.printf("Uploading %s to the Spark context...\n", piJar);
				  
				  URI uri = new URI(piJar);
				  client.addJar(uri);
//				  client.uploadJar(new File(piJar)).get();
				  int samples = 10;
				  
				  System.err.printf("Running PiJob with %d samples...\n", samples);
				  double pi = client.submit(new JavaPi2()).get();
				  reval = pi;
				  System.out.println("Pi is roughly: " + pi);
				  responseValue = "" + reval;
				} finally {
				  client.stop(true);
				}
//		Testfunction tt = new Testfunction();
//		System.out.println(tt.getValue());
//		Testfunction tt = new Testfunction();
//		
//		try {
//			responseValue = tt.getValue().toString();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(responseValue);
				
		return responseValue;
	}
}