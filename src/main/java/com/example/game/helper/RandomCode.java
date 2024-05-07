package com.example.game.helper;

public class RandomCode {
  public static String generate(int length) {
    StringBuilder code = new StringBuilder();
    Character[] validCharacters = new Character[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
        'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    for (int i = 0; i < length; i++) {
      code.append(validCharacters[(int) (Math.random() * validCharacters.length)]);
    }
    return code.toString();
  }
}
