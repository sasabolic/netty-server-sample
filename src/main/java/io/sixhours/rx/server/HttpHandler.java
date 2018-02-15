package io.sixhours.rx.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.LOCATION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@ChannelHandler.Sharable
class HttpHandler extends ChannelInboundHandlerAdapter {

    private RequestHandler handler = new RequestHandler();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;

            final Response res = handler.handle(ctx, msg);

            final FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.valueOf(res.getStatusCode()), res.getBody() != null ? Unpooled.wrappedBuffer(res.getBody()) : Unpooled.buffer(0));

            res.getHeaders().forEach((k, v) -> {
                if (k.equalsIgnoreCase(LOCATION.toString())) {
                    v = "http://" + request.headers().get(HttpHeaderNames.HOST, "unknown") + request.uri() + v;
                }
                response.headers().set(k, new AsciiString(v));
            });

            boolean keepAlive = HttpUtil.isKeepAlive(request);

            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("Error " + cause);
        ctx.close();
    }
}
