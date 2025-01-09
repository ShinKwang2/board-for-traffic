package shin.board.comment.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DecimalUtil {

    private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CHUNK_SIZE = 5;

     static int convertInt(String chunk) {

         int charsetLength = CHARSET.length();

         int value = 0;
         for (char ch : chunk.toCharArray()) {
             value = value * charsetLength + CHARSET.indexOf(ch);
         }
         return value;
     }
     static String convertString(int value) {
         int charsetLength = CHARSET.length();

         String result = "";
         for (int i = 0; i < CHUNK_SIZE; i++) {
             result = CHARSET.charAt(value % charsetLength) + result;
             value /= charsetLength;
         }
         return result;
     }
}
