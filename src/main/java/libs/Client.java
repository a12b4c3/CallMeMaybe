package libs;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;

/**
 * https://stackoverflow.com/questions/59454774/httpclient-read-response-body-in-java-11
 * https://stackabuse.com/how-to-send-http-requests-in-java/
 * https://stackoverflow.com/questions/40603889/converting-image-response-after-post-request-to-file
 */
public class Client {
    CloseableHttpClient client;
    String url;

    public Client(String urlStr) {

        this.client = HttpClientBuilder.create().build();
        this.url = urlStr;
    }

    public void sendPostAndDraw(String msg, String filename) {

        try {
            HttpPost post = new HttpPost(this.url);
            post.addHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
            post.addHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            HttpEntity entity = new ByteArrayEntity(msg.getBytes("UTF-8"));
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            System.out.println(response.getStatusLine());
            OutputStream outputStream = new FileOutputStream(new File(filename + ".png"));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = response.getEntity().getContent().read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            outputStream.close();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("http://127.0.0.1:8080/api/v7/generateDiagramImage");
        client.sendPostAndDraw("title hello_world", "result.png");
    }
}
