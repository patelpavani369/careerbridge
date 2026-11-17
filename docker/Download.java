import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

/** Tiny downloader used while building the Docker image: java Download.java <url> <file>. */
public class Download {
    public static void main(String[] a) throws Exception {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
        HttpResponse<Path> r = client.send(HttpRequest.newBuilder(URI.create(a[0])).build(),
                HttpResponse.BodyHandlers.ofFile(Path.of(a[1])));
        if (r.statusCode() != 200) throw new IllegalStateException("Download failed: HTTP " + r.statusCode() + " for " + a[0]);
        System.out.println("Downloaded " + a[1]);
    }
}
