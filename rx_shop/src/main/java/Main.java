import controller.Controller;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import reactive.ReactiveHandler;
import rx.Observable;

public class Main {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        Controller controller = new Controller(new ReactiveHandler());
        HttpServer.newServer(PORT)
                .start((req, resp) -> {
                    Observable<String> res = controller.handleRequest(req.getDecodedPath().substring(1), req.getQueryParameters());
                    resp.setStatus(HttpResponseStatus.OK);
                    return resp.writeString(res);
                })
                .awaitShutdown();
    }

}
