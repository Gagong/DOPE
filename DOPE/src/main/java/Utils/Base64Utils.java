package Utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {
    public String Encode(String v) {
        final byte[] ve = v.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(ve);
    }

    public String Decode(String v) throws UnsupportedEncodingException {
        final byte[] ve = Base64.getDecoder().decode(v);
        return new String(ve, "UTF-8");
    }
}
