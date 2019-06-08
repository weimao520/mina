import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 简单的客户端实现
 */
public class MinaClient {

    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();
        //  设置连接超时时间
        connector.setConnectTimeoutMillis(30000);
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                LineDelimiter.WINDOWS.getValue(),
                LineDelimiter.WINDOWS.getValue())));
        connector.setHandler(new DemoClientHander("你好"));
        connector.connect(new InetSocketAddress("localhost",9123));
    }

    private static class  DemoClientHander extends IoHandlerAdapter{

        private final  String valuas;
        public DemoClientHander(String valuas) {
            this.valuas = valuas;
        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            session.write(valuas);
        }
    }

}
