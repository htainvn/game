package com.example.game.redis;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.*;
import java.util.HashMap;
import org.springframework.stereotype.Component;

public class StringCompressor {
  public static String compress(String str) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip = new GZIPOutputStream(out);
    gzip.write(str.getBytes());
    gzip.close();
    return out.toString(StandardCharsets.ISO_8859_1);
  }

  public static String decompress(String str) throws IOException {
      GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes(StandardCharsets.ISO_8859_1)));
      BufferedReader bf = new BufferedReader(new InputStreamReader(gis, StandardCharsets.ISO_8859_1));
      StringBuilder outStr = new StringBuilder();
      String line;
      while ((line=bf.readLine())!=null) {
        outStr.append(line);
      }
      return outStr.toString();
  }
}
