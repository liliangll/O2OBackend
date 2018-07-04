package cn.com.efuture.o2o.backend.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FileIoHelper {

    public static byte[] inputStreamToByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        while (inputStream.read(bytes) > 0) {
            os.write(bytes);
        }
        os.flush();
        os.close();
        return os.toByteArray();
    }
}
