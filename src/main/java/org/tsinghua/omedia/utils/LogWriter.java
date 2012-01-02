package org.tsinghua.omedia.utils;

import java.io.IOException;
import java.io.Writer;
import java.nio.CharBuffer;

import org.apache.log4j.Logger;

public class LogWriter extends Writer {
    
    private Logger logger;
    private CharBuffer buffer;
    
    public LogWriter(Logger logger) {
        this.logger = logger;
        buffer = CharBuffer.allocate(4096);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        int putLen = 0;
        while(putLen < len) {
            if(!buffer.hasRemaining()) {
                flush();
            }
            if(buffer.remaining() >= len-putLen) {
                buffer.put(cbuf, off+putLen, len-putLen);
                putLen = len;
            } else {
                int remaining = buffer.remaining();
                buffer.put(cbuf, off+putLen, buffer.remaining());
                putLen += remaining;
            }
        }
    }

    @Override
    public void flush() throws IOException {
        buffer.flip();
        logger.info(buffer.toString());
        buffer.clear();
    }

    @Override
    public void close() throws IOException {
        flush();
    }

}
