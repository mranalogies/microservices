package mranalogies.microservices.vertx.todoservice.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.redis.RedisClient;
import mranalogies.microservices.vertx.todoservice.Constants;

import java.util.List;

public class SingleApplicationVerticle extends AbstractVerticle {
    private static final String HOST = "0.0.0.0";
    private static final String REDIS_HOST = "127.0.0.1";
    private static final int PORT = 8082;
    private static final int REDIS_PORT = 6379;

    private RedisClient redis;

    @Override
    public Vertx getVertx() {
        return super.getVertx();
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
    }

    @Override
    public String deploymentID() {
        return super.deploymentID();
    }

    @Override
    public JsonObject config() {
        return super.config();
    }

    @Override
    public List<String> processArgs() {
        return super.processArgs();
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        // routes
        router.get(Constants.API_GET).handler(this::handleGetTodo);
        router.get(Constants.API_LIST_ALL).handler(this::handleGetAll);
        router.post(Constants.API_CREATE).handler(this::handleCreateTodo);
        router.patch(Constants.API_UPDATE).handler(this::handleUpdateTodo);
        router.delete(Constants.API_DELETE).handler(this::handleDeleteOne);
        router.delete(Constants.API_DELETE_ALL).handler(this::handleDeleteAll);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(PORT, HOST, result -> {
                    if (result.succeeded())
                        startFuture.complete();
                    else
                        startFuture.fail(result.cause());
                });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }

    @Override
    public void start() throws Exception {
        super.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private
}
